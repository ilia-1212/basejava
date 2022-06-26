package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Object key, Resume r) {
        storage[(int) key] = r;
    }

    @Override
    protected void doSave(Object key, Resume r) {
        int index = (int) key;
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume(index, r);
        size++;
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(int) key];
    }

    @Override
    protected void doDelete(Object key) {
        deleteResume((int) key);
        storage[--size] = null;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(int index, Resume r);
}