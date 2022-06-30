package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doUpdate(Object key, Resume r) {
        storage.put(((Resume) key).getUuid(), r);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get(((Resume) key).getUuid());
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage.values());
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
