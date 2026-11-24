package com.ananyaa.docbrain.repository;

import com.ananyaa.docbrain.entity.DocumentMetadata;
import com.ananyaa.docbrain.entity.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
public interface DocumentMetadataRepo extends JpaRepository<DocumentMetadata, UUID>{
    List<DocumentMetadata> findByStatus(DocumentStatus status);
    List<DocumentMetadata> findAllByOrderByCreatedAtDesc();

}
