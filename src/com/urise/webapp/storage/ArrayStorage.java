package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insertResume(int index, Resume r) {
        storage[size] = r;
    }

    @Override
    protected Integer getSearchKey(Object key) {
        for (int i = 0; i < size; i++) {
            if (((String) key).equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}