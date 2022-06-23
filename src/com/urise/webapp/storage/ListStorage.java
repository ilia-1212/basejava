package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.LinkedList;

public class ListStorage extends AbstractStorage {
    // protected ArrayList<Resume> storage = new ArrayList<>();

    protected LinkedList<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Object key, Object r) {
        storage.set((int) key, (Resume) r);
    }

    @Override
    protected void doSave(Object key, Object r) {
        storage.add((Resume) r);
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
            Resume searchObject  = new Resume((String) key);
            searchKey =  storage.indexOf(searchObject);
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
