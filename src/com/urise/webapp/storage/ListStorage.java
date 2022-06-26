package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Object key, Resume r) {
        storage.set((int) key, r);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((int) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((int) key);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object findSearchKey(Object key) {
        int searchKey = -1;
        try {
            Resume searchObject = new Resume((String) key);
            searchKey = storage.indexOf(searchObject);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return searchKey;
    }

    @Override
    protected boolean isExist(Object key) {
        return (int) key >= 0;
    }
}
