package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.LinkedList;

public class ListStorage extends AbstractStorage {
   // protected ArrayList<Resume> storage = new ArrayList<>();
    protected LinkedList<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
        //storage.trimToSize();
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        storage.set(index, r);
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(r, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(index);
    }

    @Override
    public Resume[] getAll() {
       Resume[] tmp = new Resume[storage.size()];
        return storage.toArray(tmp);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }

    @Override
    protected void insertResume(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }
}
