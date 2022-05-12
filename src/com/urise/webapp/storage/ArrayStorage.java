package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage;
    private int size;
    protected static final int STORAGE_LIMIT = 10000;

    public ArrayStorage() {
        storage = new Resume[STORAGE_LIMIT];
        size = 0;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] , массив будет переполнен!");
        } else if (getIndex(r.getUuid()) == -1) {
            storage[size++] = r;
        } else {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] уже есть!");
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Ошибка при  обновлении, uuid [" + r.getUuid() + "] не найден!");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Ошибка при поиске, uuid [" + uuid + "] не найден!");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}