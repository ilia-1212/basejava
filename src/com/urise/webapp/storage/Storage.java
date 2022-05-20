package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {
    void clear();

    void save(Resume r);

    void update(String oldUuid, String newUuid);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}