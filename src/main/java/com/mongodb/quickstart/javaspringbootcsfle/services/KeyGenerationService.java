package com.mongodb.quickstart.javaspringbootcsfle.services;

import java.io.IOException;
import java.util.Map;

public interface KeyGenerationService {

    void generateLocalMasterKey() throws IOException;

    Map<String, Map<String, Object>> getKmsProviders();

    String generateLocalKeyId(String keyVaultNamespace, Map<String, Map<String, Object>> kmsProviders,
                                     String connectionString);
}
