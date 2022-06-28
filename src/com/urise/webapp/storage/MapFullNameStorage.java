package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapFullNameStorage implements Storage {
    protected Map<String, Resume> storage = new HashMap<>();

    protected String getSearchKey(Object key) {
        for ( Map.Entry<String, Resume> map : storage.entrySet()) {
            if (((String) key).equals(map.getKey())) {
                return map.getKey();
            }
        }
        return null;
    }

    protected void doUpdate(Object key, Resume r) {
        storage.put((String) key, r);
    };

    protected boolean isExist(Object key) {
        return (String) key != null;
    };

    protected void doSave(Object key, Resume r) {
        storage.putIfAbsent(r.getFullName(), r);
    };

    protected Resume doGet(Object key) {
      return storage.get((String) key);
    };

    protected void doDelete(Object key) {
        storage.remove((String) key);
    };

    @Override
    public void clear() {
        storage.clear();
    }

    public void update(Resume r) {
        Object key = getExistedSearchKey(r.getFullName());
        doUpdate(key, r);
    }

    public void save(Resume r) {
        Object key = getNotExistedSearchKey(r.getFullName());
        doSave(key, r);
    }

    public void delete(String uuid) {
        Object key = getExistedSearchKey(uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        Object key = getExistedSearchKey(uuid);
        return doGet(key);
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

    protected Object getExistedSearchKey(String key) {
        Object searchKey = getSearchKey(key);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(key);
        }
        return searchKey;
    }

    protected Object getNotExistedSearchKey(String key) {
        Object searchKey = getSearchKey(key);
        if (isExist(searchKey)) {
            throw new ExistStorageException(key);
        }
        return searchKey;
    }
}
