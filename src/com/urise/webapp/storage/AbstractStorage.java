package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> RESUME_FULLNAME_UUID_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract Object getSearchKey(String key);

    protected abstract void doUpdate(Object key, Resume r);

    protected abstract boolean isExist(Object key);

    protected abstract void doSave(Object key, Resume r);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract List<Resume> getAll();

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

    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list, RESUME_FULLNAME_UUID_COMPARATOR);
        return list;
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
