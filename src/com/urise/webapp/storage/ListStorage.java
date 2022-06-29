package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new LinkedList<>();

    @Override
    // переопределили Object на Integer
    protected Integer getSearchKey(String key) {
        // не подходит indexOf из-за сравнения всего объекта , нужно сравнить по Uuid
        for (int i = 0; i < storage.size(); i++) {
            if (key.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

    @Override
    protected void doUpdate(Object key, Resume r) {
        storage.set((Integer) key, r);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(((Integer) key).intValue());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(List.copyOf(storage));
    }

    @Override
    public int size() {
        return storage.size();
    }
}