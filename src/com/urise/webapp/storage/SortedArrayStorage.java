package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

//    private static class ResumeComparator implements Comparator<Resume> {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }

    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

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
    protected Object findSearchKey(Object key) {
        int searchKey = -1;
        try {
            Resume searchObject  = new Resume((String) key);
            searchKey =  Arrays.binarySearch(storage, 0, size, searchObject, RESUME_COMPARATOR);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return searchKey;
    }

    @Override
    protected boolean isExist(Object key) {
        return ((int) key >= 0) ? true : false;
    }
}
