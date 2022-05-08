import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage;
    private int size;

    public ArrayStorage() {
        storage = new Resume[4];
        size = 0;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (getIndex(r.uuid) == -1) {
            storage[size++] = r;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            System.arraycopy(storage, (index + 1), storage, index, (storage.length - index - 1));
            storage[storage.length - 1] = null;
            size--;
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
        for(int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }

    private Resume[] shiftArray(Resume[] resumes, int index) {
        Resume[] tmp = new Resume[resumes.length - 1];
        System.arraycopy(resumes, (index + 1), tmp, index, (resumes.length - index - 1));
        System.arraycopy(resumes, 0, tmp, 0, index);
        return tmp;
    }
}