package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    public void saveOverflow() throws Exception {
        throw new StorageException("Storage overflow","42");
    }
}
