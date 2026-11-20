package com.ananyaa.docbrain.repository;

import com.ananyaa.docbrain.entity.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface DocumentMetadataRepo extends JpaRepository<DocumentMetadata, UUID>{
}
