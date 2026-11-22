package com.ananyaa.docbrain.service;

import com.ananyaa.docbrain.dto.DocumentResponseDto;
import com.ananyaa.docbrain.entity.DocumentMetadata;
import com.ananyaa.docbrain.entity.DocumentStatus;
import com.ananyaa.docbrain.repository.DocumentMetadataRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ai.document.Document;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentMetadataService {

    private static final Logger log =LoggerFactory.getLogger(DocumentMetadataService.class);
    private final DocumentMetadataRepo documentMetadataRepo;
    private final DocumentParserService parserService;
    private final DocumentIngestionService ingestionService;
    private final JdbcTemplate jdbcTemplate;


    // method to upload and parse document
    public DocumentResponseDto uploadAndProcess(MultipartFile file) {

        String fileName = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "document";

        String contentType = file.getContentType() != null
                ? file.getContentType()
                : "application/octet-stream";

        //document meta data create
        //storing the user detail with document metadata
        DocumentMetadata documentMetadata = DocumentMetadata
                .builder()
                .filename(fileName)
                .contentType(contentType)
                .status(DocumentStatus.UPLOADING)
                .fileSize(file.getSize())
                .build();

        //save the document Metadata
        documentMetadata = documentMetadataRepo.save(documentMetadata);

        //parse the file
        List<Document> parsedDocs= parserService.parse(file);

        //ingest service
        int chunksCreated= ingestionService.ingest(documentMetadata, parsedDocs);

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
}
