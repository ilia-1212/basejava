package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteResume(int index) {
        int indexDeleted = size - index - 1;
        if (indexDeleted > 0 ) {
            System.arraycopy(storage, index + 1, storage, index, indexDeleted);
        }
    }

    @Override
    protected void insertResume(Resume r, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
