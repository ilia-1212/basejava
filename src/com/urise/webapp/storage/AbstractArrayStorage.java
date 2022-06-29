package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Object key, Resume r) {
        storage[(Integer) key] = r;
    }

    @Override
    protected List<Resume> getAll() {
        Resume[] array = Arrays.copyOfRange(storage, 0, size);
        return Arrays.asList(array);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume((Integer) key, r);
        size++;
    }

    @Override
    protected void doDelete(Object key) {
        deleteResume((Integer) key);
        storage[--size] = null;
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(Integer) key];
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(int index, Resume r);

    protected abstract Integer getSearchKey(String key);
}