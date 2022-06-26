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
    protected Object findSearchKey(Object key) {
        int searchKey = -1;
        for (int i = 0; i < size; i++) {
            if (key.equals(storage[i].getUuid())) {
                searchKey = i;
                break;
            }
        }
        return searchKey;
    }

    @Override
    protected boolean isExist(Object key) {
        return (int) key >= 0;
    }
}