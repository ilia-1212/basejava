package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveOverflow() throws Exception {
        throw new StorageException("Storage overflow","42");
    }
}
