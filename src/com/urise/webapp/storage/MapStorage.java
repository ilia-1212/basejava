package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new TreeMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Object key, Resume r) {
        storage.put((String) key, r);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((String) key);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object findSearchKey(Object key) {
        return key;
    }

    @Override
    protected boolean isExist(Object key) {
        return storage.containsKey((String) key);
    }
}
