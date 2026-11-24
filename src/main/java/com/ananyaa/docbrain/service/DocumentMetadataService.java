package com.ananyaa.docbrain.service;

import com.ananyaa.docbrain.dto.DocumentMetadataDto;
import com.ananyaa.docbrain.dto.DocumentResponseDto;
import com.ananyaa.docbrain.entity.DocumentMetadata;
import com.ananyaa.docbrain.entity.DocumentStatus;
import com.ananyaa.docbrain.exception.DocumentProcessingException;
import com.ananyaa.docbrain.exception.ResourceNotFoundException;
import com.ananyaa.docbrain.repository.DocumentMetadataRepo;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ai.document.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DocumentMetadataService {

    private static final Logger log =LoggerFactory.getLogger(DocumentMetadataService.class);
    private final DocumentMetadataRepo documentMetadataRepo;
    private final DocumentParserService parserService;
    private final DocumentIngestionService ingestionService;
    private final JdbcTemplate jdbcTemplate;
    private final ModelMapper modelMapper;


    // method to upload and parse document
    public DocumentResponseDto uploadAndProcess(MultipartFile file) {

        String fileName = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "document";

        String contentType = file.getContentType() != null
                ? file.getContentType()
                : "application/octet-stream";

        //document metadata create
        //storing the user detail with document metadata
        DocumentMetadata documentMetadata = DocumentMetadata
                .builder()
                .filename(fileName)
                .contentType(contentType)
                .status(DocumentStatus.UPLOADING)
                .fileSize(file.getSize())
                .createdAt(LocalDateTime.now())
                .build();

        //save the document Metadata
        documentMetadata = documentMetadataRepo.save(documentMetadata);

        List<Document> parsedDocs = null;
        int chunksCreated = 0;

        try {
            //parse the file
            parsedDocs = parserService.parse(file);

            //ingest service
            chunksCreated = ingestionService.ingest(documentMetadata, parsedDocs);
            if (chunksCreated == 0) {
                throw new DocumentProcessingException("Document appears to be empty or unscannable");
            }
        } catch (RuntimeException e) {
            log.warn("Removing document metadata after upload processing failed [id={}, fileName={}]",
                    documentMetadata.getId(), documentMetadata.getFilename(), e);

            documentMetadataRepo.deleteById(documentMetadata.getId());
            documentMetadataRepo.flush();
            throw e;
        }

        //documentMetadata.setTotalChunks(chunksCreated);

        return DocumentResponseDto.builder()
                .id(documentMetadata.getId())
                .fileName(documentMetadata.getFilename())
                .fileSize(documentMetadata.getFileSize())
                .chunksCreated(chunksCreated)
                .status(documentMetadata.getStatus())
                .message("Document successfully processed and indexed.")
                .build();

    }

    public List<DocumentResponseDto> uploadMultipleDocuments(List<MultipartFile> files) {
        List<DocumentResponseDto> responseDtos = new ArrayList<>();

        for (MultipartFile file : files) {
            DocumentResponseDto result = this.uploadAndProcess(file);
            responseDtos.add(result);
        }
        return responseDtos;
    }

    public List<DocumentMetadataDto> getAllDocuments() {
        List<DocumentMetadata> allDocuments = documentMetadataRepo.findAllByOrderByCreatedAtDesc();
        return allDocuments.stream()
                .map(documentMetadata -> modelMapper.map(documentMetadata, DocumentMetadataDto.class))
                .toList();
    }

    public DocumentMetadataDto getDocumentById(UUID id) {
        DocumentMetadata documentMetadata = documentMetadataRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document with given id not found !!"));
        return modelMapper.map(documentMetadata, DocumentMetadataDto.class);
    }

    public void deleteDocument(UUID id) {
        DocumentMetadata documentMetadata = documentMetadataRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document with given id not found !!"));


        documentMetadataRepo.delete(documentMetadata);
        //delete the vector entries
        try {
            String deleteVectorsSql = "DELETE FROM public.vector_store WHERE metadata->>'documentId' = ?";

            int deletedCount = jdbcTemplate.update(deleteVectorsSql, id.toString());

            log.info("Deleted {} vector chunks for document id {}", deletedCount, id);

        } catch (Exception e) {
            log.warn("Could not delete vectors from vector store directly: {}", e.getMessage());
        }

    }
}
