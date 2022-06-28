package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;

public class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    public void saveOverflow() throws Exception {
        throw new StorageException("Storage overflow","42");
    }
}
