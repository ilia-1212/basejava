package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) throws NotExistStorageException {
        Object key = findExistingSearchKey(r.getUuid());
        doUpdate(key, r);
    }

    public void save(Resume r) throws ExistStorageException {
        Object key = findNotExistingSearchKey(r.getUuid());
        doSave(key, r);
    }

    public Resume get(String uuid) throws NotExistStorageException {
        Object key = findExistingSearchKey(uuid);
        return doGet(key);
    }

    public void delete(String uuid) throws NotExistStorageException {
        Object key = findExistingSearchKey(uuid);
        doDelete(key);
    }

    protected Object findExistingSearchKey(String key) throws NotExistStorageException {
        Object searchKey = findSearchKey(key);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(key);
        }
        return searchKey;
    }

    protected Object findNotExistingSearchKey(String key) throws ExistStorageException {
        Object searchKey = findSearchKey(key);
        if (isExist(searchKey)) {
            throw new ExistStorageException(key);
        }
        return searchKey;
    }

    protected abstract void doUpdate(Object key, Object r);

    protected abstract void doSave(Object key, Object r);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract Object findSearchKey(Object key);

    protected abstract boolean isExist(Object key);
}
