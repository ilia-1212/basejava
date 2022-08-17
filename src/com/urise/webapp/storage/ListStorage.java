package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String key) {
        for (int i = 0; i < storage.size(); i++) {
            if (key.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer key) {
        return key != null;
    }

    @Override
    protected void doUpdate(Integer key, Resume r) {
        storage.set(key, r);
    }

    @Override
    protected void doSave(Integer key, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Integer key) {
        storage.remove((key).intValue());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(List.copyOf(storage));
    }

    @Override
    public int size() {
        return storage.size();
    }
}