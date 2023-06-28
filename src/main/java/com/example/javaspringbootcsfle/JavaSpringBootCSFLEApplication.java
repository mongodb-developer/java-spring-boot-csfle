package com.example.javaspringbootcsfle;

import com.example.javaspringbootcsfle.services.KeyGenerationService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import com.mongodb.AutoEncryptionSettings;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.javaspringbootcsfle.constants.DBStrings.*;
import static com.mongodb.client.model.Filters.eq;


@SpringBootApplication
public class JavaSpringBootCSFLEApplication {

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Value("${spring.data.mongodb.database}")
    private String DATABASE;

    @Value("${spring.data.mongodb.collection}")
    private String COLLECTION;

    @Value("${crypt.shared.lib.path}")
    private String CRYPT_SHARED_LIB_PATH;

    private KeyGenerationService keyGenerationService;

    private static final String KEY_VAULT_NAMESPACE = KEY_VAULT_DB + "." + KEY_VAULT_COLL;

    public JavaSpringBootCSFLEApplication(KeyGenerationService keyGenerationService) {
        this.keyGenerationService = keyGenerationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringBootCSFLEApplication.class, args);
    }

    @Bean
    public MongoClient mongoClient() {

        final Map<String, Map<String, Object>> kmsProviders = keyGenerationService.getKmsProviders();

        // We can use the key generation service or use a hardcoded DEK for our example
        final String localDEK = keyGenerationService.generateLocalKeyId(KEY_VAULT_NAMESPACE, kmsProviders, connectionString);

        //String localDEK = "<<paste-base-64-encoded-data-encryption-key-id>>";

        final Map<String, BsonDocument> schemaMap = generateSchemaMap(localDEK);


        //crypt_shared path
        Map<String, Object> extraOptions = new HashMap<String, Object>();
        extraOptions.put("cryptSharedLibPath", CRYPT_SHARED_LIB_PATH);


        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .autoEncryptionSettings(AutoEncryptionSettings.builder()
                        .keyVaultNamespace(KEY_VAULT_NAMESPACE)
                        .kmsProviders(kmsProviders)
                        .schemaMap(schemaMap)
                        .extraOptions(extraOptions)
                        .build())
                .build();

        // The below code could be used to test
//        Document docSecure = MongoClients.create(clientSettings).getDatabase("personsDB").getCollection("personsEncrypted").find(eq("firstName", "Megha")).first();
//        System.out.println(docSecure.toJson());

        return MongoClients.create(clientSettings);
    }

    /**
     * Generate the schema map needed for automatic encryption
     * @param DEK_ID
     * @return
     */
    private Map<String, BsonDocument> generateSchemaMap(final String DEK_ID) {

        Document jsonSchema = new Document().append("bsonType", "object").append("encryptMetadata",
                        new Document().append("keyId", new ArrayList<>((Arrays.asList(new Document().append("$binary", new Document()
                                .append("base64", DEK_ID)
                                .append("subType", "04")))))))
                .append("properties", new Document()
                        .append("aadharNumber", new Document().append("encrypt", new Document()
                                .append("bsonType", "string")
                                .append("algorithm", "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic"))));

        Map<String, BsonDocument> schemaMap = new HashMap<String, BsonDocument>();
        schemaMap.put(DATABASE + "." + COLLECTION, BsonDocument.parse(jsonSchema.toJson()));

        return schemaMap;
    }
}
