package com.urise.webapp.storage;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(STORAGE_DBURL,STORAGE_DBUSER,STORAGE_DBPASS));
    }
}
