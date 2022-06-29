package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapResumeStorage extends AbstractStorage {
    protected Map<Resume, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String key) {
        for (Map.Entry node : storage.entrySet()) {
            if (key.equals(((Resume) node.getKey()).getUuid())) {
                return node.getKey();
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
        storage.put((Resume) key, r);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        storage.put(r, r);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(key);
    }

    @Override
    protected List<Resume> getAll() {
        return storage.values().stream().collect(Collectors.toList());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
