package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("uuid [" + uuid + "] уже есть!", uuid);
    }
}
