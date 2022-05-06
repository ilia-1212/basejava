import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, storage.length, null);
    }

    void save(Resume r) {
        if (get(r.uuid) == null) {
            storage[size()] = r;
        }
    }

    Resume get(String uuid) {
        int idx = getIndex(uuid);
        if (idx != -1) return storage[idx];
        else return null;
    }

    void delete(String uuid) {
        for(int i = getIndex(uuid); i != -1 && i < storage.length; i++) {
            if (i != storage.length - 1) {
                storage[i] = storage[i + 1];
            } else storage[i] = null;
        }
    }

    int getIndex(String uuid) {
        for(int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] tmp;
        tmp = new Resume[storage.length];
        int idx = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                tmp[idx] = storage[i];
                idx++;
            }
        }
        return Arrays.copyOf(tmp,idx);
    }

    int size() {
        return getAll().length;
    }
}