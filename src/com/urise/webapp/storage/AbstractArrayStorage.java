package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final int size() {
        return size;
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[--size] = null;
        } else {
            System.out.println("Ошибка при удалении, uuid [" + uuid + "] не найден!");
        }
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] , массив будет переполнен!");
        } else if (index >= 0) {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] уже есть!");
        } else {
           insertResume(r, index);
           size++;
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("Ошибка при  обновлении, uuid [" + r.getUuid() + "] не найден!");
        }
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

    protected abstract void deleteResume(int index);
    protected abstract void insertResume(Resume r, int index);
    protected abstract int getIndex(String uuid);
}