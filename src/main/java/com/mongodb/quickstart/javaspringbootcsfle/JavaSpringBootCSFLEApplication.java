package com.mongodb.quickstart.javaspringbootcsfle;

import com.mongodb.AutoEncryptionSettings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.quickstart.javaspringbootcsfle.services.KeyGenerationService;
import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.quickstart.javaspringbootcsfle.constants.DBStrings.KEY_VAULT_COLL;
import static com.mongodb.quickstart.javaspringbootcsfle.constants.DBStrings.KEY_VAULT_DB;

@SpringBootApplication
public class JavaSpringBootCSFLEApplication {

    private static final String KEY_VAULT_NAMESPACE = KEY_VAULT_DB + "." + KEY_VAULT_COLL;
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;
    @Value("${spring.data.mongodb.database}")
    private String DATABASE;
    @Value("${spring.data.mongodb.collection}")
    private String COLLECTION;
    @Value("${crypt.shared.lib.path}")
    private String CRYPT_SHARED_LIB_PATH;
    private final KeyGenerationService keyGenerationService;

    public JavaSpringBootCSFLEApplication(KeyGenerationService keyGenerationService) {
        this.keyGenerationService = keyGenerationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringBootCSFLEApplication.class, args);
    }

    @Bean
    public MongoClient mongoClient() {

//        String dekId = "<paste-base-64-encoded-data-encryption-key-id>>";

        final Map<String, Map<String, Object>> kmsProviders = keyGenerationService.getKmsProviders();

        final String localDEK = keyGenerationService.generateLocalKeyId(KEY_VAULT_NAMESPACE, kmsProviders, connectionString);

        //String localDEK = "<<Paste ur dek id here>>";

        final Map<String, BsonDocument> schemaMap = generateSchemaMap(localDEK);

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
//        Document docSecure = MongoClients.create(clientSettings).getDatabase("personsDB").getCollection("personsEncrypted").find(eq("firstName", "Megha")).first();
//        System.out.println(docSecure.toJson());

        return MongoClients.create(clientSettings);
    }

    private Map<String, BsonDocument> generateSchemaMap(final String DEK_ID) {

        Document jsonSchema = new Document().append("bsonType", "object").append("encryptMetadata",
                        new Document().append("keyId", new ArrayList<>((Collections.singletonList(new Document().append("$binary", new Document()
                                .append("base64", DEK_ID)
                                .append("subType", "04")))))))
                .append("properties", new Document()
                        .append("passportNumber", new Document().append("encrypt", new Document()
                                .append("bsonType", "string")
                                .append("algorithm", "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic"))));

        Map<String, BsonDocument> schemaMap = new HashMap<String, BsonDocument>();
        schemaMap.put(DATABASE + "." + COLLECTION, BsonDocument.parse(jsonSchema.toJson()));

        return schemaMap;
    }
}
