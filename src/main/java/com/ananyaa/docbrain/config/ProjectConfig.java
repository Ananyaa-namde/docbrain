package com.ananyaa.docbrain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {


        @Bean
        public OpenAPI openAPI() {

            String securitySchemeName = "bearerAuth";

            return new OpenAPI()
                    .info(
                            new Info()
                                    .title("DocBrain — AI Document Intelligence & RAG backend")
                                    .description("REST API for DocBrain: Multi-format document ingestion, vector embeddings with PostgreSQL pgvector, and hybrid conversational Q&A with OpenAI.")

                                    .version("1.0.0")
                                    .contact(new Contact()
                                            .name("Ananyaa")
                                            .email("namdeananyaa07@gmail.com")
                                            .url("https://github.com/Ananyaa-namde")
                                    )
                );
    }
}