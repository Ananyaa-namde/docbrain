package com.ananyaa.docbrain.service;

import com.ananyaa.docbrain.config.AppProperties;
import com.ananyaa.docbrain.entity.DocumentMetadata;
import com.ananyaa.docbrain.repository.DocumentMetadataRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DocumentIngestionService {
    private static final Logger log= LoggerFactory.getLogger(DocumentIngestionService.class);

    private  final VectorStore vectorStore;
    private final DocumentMetadataRepo documentMetadataRepo;
    private final AppProperties appProperties;
    public int ingest(DocumentMetadata metadata, List<Document> documents) {
        return 0;
    }
}
