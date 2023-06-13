package com.example.javaspringbootcsfle.constants;

import org.springframework.beans.factory.annotation.Value;

public interface DBStrings {

    String KEY_VAULT_DB = "encryptionVault";
    String KEY_VAULT_COLL = "keyVault";
    String KMS_PROVIDER = "local";
    String MASTER_KEY_FILE_PATH = "./master-key.txt";

}
