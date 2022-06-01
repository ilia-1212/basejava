package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @After
    public void exitTest() throws Exception {
        storage.clear();
    }

    @Test
    public void size() throws Exception {
           Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        Assert.assertSame(r, storage.get(UUID_1));
    }

    @Test
    public void getAll()  throws Exception {
        Resume[] r_all = storage.getAll();
        for (Resume r : r_all) {
            Assert.assertSame(storage.get(r.getUuid()), r);
        }
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume(UUID_4);
        storage.clear();
        storage.save(r);
        Assert.assertEquals(1, storage.size());
        Assert.assertSame(r, storage.get(UUID_4));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
        Assert.assertEquals(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void overFlowStorage() {
        storage.clear();
        try {
            storage.save(new Resume(UUID_1));
            storage.save(new Resume(UUID_2));
            storage.save(new Resume(UUID_3));
        } catch (StorageException e) {
           Assert.fail("overflow storage while inserting");
        }
        storage.save(new Resume(UUID_4));
    }
}