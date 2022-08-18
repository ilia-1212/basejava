package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamPathStorage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super( new PathStorage(STORAGE_DIR.getPath(),new ObjectStreamPathStorage()));
    }
}
