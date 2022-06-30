package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (f1,f2) -> f1.getUuid().compareTo(f2.getUuid());

    @Override
    protected void deleteResume(int index) {
        int indexDeleted = size - index - 1;
        if (indexDeleted > 0) {
            System.arraycopy(storage, index + 1, storage, index, indexDeleted);
        }
    }

    @Override
    protected void insertResume(int index, Resume r) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    protected Integer getSearchKey(String key) {
        Resume searchObject  = new Resume(key, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchObject, RESUME_COMPARATOR);
    }
}
