package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String resume) {
        return storage.get(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
