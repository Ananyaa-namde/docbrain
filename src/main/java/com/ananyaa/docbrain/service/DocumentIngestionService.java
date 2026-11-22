package com.ananyaa.docbrain.service;

import com.ananyaa.docbrain.entity.DocumentMetadata;
import org.springframework.stereotype.Service;

import org.springframework.ai.document.Document;
import java.util.List;


@Service
public class DocumentIngestionService {
    public int ingest(DocumentMetadata metadata, List<Document> documents) {
        return 0;
    }
}
