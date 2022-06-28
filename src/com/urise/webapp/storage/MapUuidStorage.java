package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MapUuidStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new TreeMap<>();

    @Override
    // заменили Object на String
    protected String getSearchKey(Object key) {
        for ( Map.Entry<String, Resume> map : storage.entrySet()) {
            if (((String) key).equals(map.getKey())) {
                return map.getKey();
            }
        }
        return null;
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
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Comparator<Resume> resumeFullNameUuidComparator = (h1, h2) -> {
            if (h1.getFullName().equals(h2.getFullName())) {
                return h1.getUuid().compareTo(h2.getUuid());
            } else {
                return h1.getFullName().compareTo(h2.getFullName());
            }
        };
        return storage.values().stream().sorted(resumeFullNameUuidComparator).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object key) {
        return (String) key != null;
    }
}
