package com.ananyaa.docbrain.service;

import com.ananyaa.docbrain.dto.DocumentResponseDto;
import com.ananyaa.docbrain.repository.DocumentMetadataRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentMetadataService {
    private static final Logger log = LoggerFactory.getLogger(DocumentMetadataService.class);

    private final DocumentMetadataRepo documentMetadataRepo;
    private final JdbcTemplate jdbcTemplate;


    //method to upload and parse document
    public DocumentResponseDto uploadAndProcess(MultipartFile file) {

        return null;
    }
}
