package com.urise.webapp.storage;

import com.urise.webapp.exception.*;
import com.urise.webapp.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_FOUND = "dummy";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @After
    public void exitTest() throws Exception {
        storage.clear();
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        Assert.assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    public void update() throws Exception {
        storage.update(RESUME_1);
        assertGet(RESUME_1);
    }

    @Test
    public void getAll()  throws Exception {
        Resume[] r_all = storage.getAll();
        Resume[] expected =  new Resume[] {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, r_all);
        assertSize(3);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_3);
        assertSize(2);
        assertGet(RESUME_3);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NOT_FOUND);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        int indexOver = 0;
        storage.clear();
        try {
            storage.save(RESUME_1);
            storage.save(RESUME_2);
            storage.save(RESUME_3);
            indexOver = storage.size();
            for (int i = storage.size(); i < 10000; i++) {
                indexOver++;
                storage.save(new Resume());
            }

        } catch (StorageException e) {
           Assert.fail("overflow storage while inserting index " + indexOver);
        }
        storage.save(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_NOT_FOUND);
    };

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(storage.get(UUID_NOT_FOUND));
    };

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    };

    protected void assertSize(int expected) {
        Assert.assertEquals(expected, storage.size());
    };

    protected void assertGet(Resume r) {
        Assert.assertEquals(r, storage.get(r.getUuid()));
    };
}