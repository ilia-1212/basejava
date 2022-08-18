package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Integer key, Resume r) {
        storage[key] = r;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected void doSave(Integer key, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume(key, r);
        size++;
    }

    @Override
    protected void doDelete(Integer key) {
        deleteResume(key);
        storage[--size] = null;
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage[key];
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(int index, Resume r);

    protected abstract Integer getSearchKey(String key);
}