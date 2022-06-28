package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(Object key);

    protected abstract void doUpdate(Object key, Resume r);

    protected abstract boolean isExist(Object key);

    protected abstract void doSave(Object key, Resume r);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    public void update(Resume r) {
        Object key = getExistedSearchKey(r.getUuid());
        doUpdate(key, r);
    }

    public void save(Resume r) {
        Object key = getNotExistedSearchKey(r.getUuid());
        doSave(key, r);
    }

    public void delete(String uuid) {
        Object key = getExistedSearchKey(uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        Object key = getExistedSearchKey(uuid);
        return doGet(key);
    }

    protected Object getExistedSearchKey(String key) {
        Object searchKey = getSearchKey(key);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(key);
        }
        return searchKey;
    }

    protected Object getNotExistedSearchKey(String key) {
        Object searchKey = getSearchKey(key);
        if (isExist(searchKey)) {
            throw new ExistStorageException(key);
        }
        return searchKey;
    }
}
