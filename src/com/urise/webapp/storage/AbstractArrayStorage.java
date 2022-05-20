package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, storage.length - index - 1);
            storage[storage.length - 1] = null;
            size--;
        } else {
            System.out.println("Ошибка при удалении, uuid [" + uuid + "] не найден!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Ошибка при поиске, uuid [" + uuid + "] не найден!");
            return null;
        }
    }

    public final int size() {
        return size;
    }

    public abstract void save(Resume r);

    public abstract void update(String oldUuid, String newUuid);

    protected abstract int getIndex(String uuid);
}