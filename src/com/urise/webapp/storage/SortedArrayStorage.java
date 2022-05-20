package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(String oldUuid, String newUuid) {
        delete(oldUuid);
        Resume object = new Resume();
        object.setUuid(newUuid);
        save(object);
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] , массив будет переполнен!");
        } else if (index < 0) {
            index = - index - 1;
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = r;
            size++;
        } else {
            System.out.println("Ошибка при сохранении, uuid [" + r.getUuid() + "] уже есть!");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
