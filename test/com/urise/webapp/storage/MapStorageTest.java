package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void saveOverflow() throws Exception {
        throw new StorageException("Storage overflow","42");
    }
}
