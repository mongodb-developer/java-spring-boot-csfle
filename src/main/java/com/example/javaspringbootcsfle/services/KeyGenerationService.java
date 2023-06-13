package com.example.javaspringbootcsfle.services;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.bson.Document;
import org.springframework.stereotype.Service;

public interface KeyGenerationService {

    public void generateLocalMasterKey() throws IOException;
    public Map<String, Map<String, Object>> getKmsProviders();
    public String generateLocalKeyId(String keyVaultNamespace, Map<String, Map<String, Object>> kmsProviders,
                                     String connectionString);

}
