package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("uuid [" + uuid + "] не найден!", uuid);
    }
}
