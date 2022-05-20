package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(String oldUuid, String newUuid) {
        int index = getIndex(oldUuid);
        if (index >= 0) {
            Resume newObj = new Resume();
            newObj.setUuid(newUuid);
            storage[index] = newObj;
        } else {
            System.out.println("Ошибка при  обновлении, uuid [" + oldUuid + "] не найден!");
        }
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] , массив будет переполнен!");
        } else if (getIndex(r.getUuid()) < 0) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] уже есть!");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}