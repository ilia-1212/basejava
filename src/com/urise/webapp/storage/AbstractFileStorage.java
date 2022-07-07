package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected File directory;
    protected FilenameFilter filter = (dir, mask) -> mask.endsWith(".resume");

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles(filter);
        if (listFiles != null) {
            for (File file : directory.listFiles(filter)) {
                file.delete();
            }
        } else {
            throw new StorageException("listFiles is null", "");
        }
    }

    @Override
    public int size() {
        int count = 0;
        File[] listFiles = directory.listFiles(filter);
        if (listFiles != null) {
            for (File file : directory.listFiles(filter)) {
                count += file.length();
            }
        } else {
            throw new StorageException("listFiles is null", "");
        }
        return count;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWrite(file, r);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
            doWrite(file, r);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    protected abstract void doWrite(File file, Resume r) throws IOException;

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("file is null", "");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = null;
        File[] listFiles = directory.listFiles(filter);
        if (listFiles != null) {
            for (File file : directory.listFiles(filter)) {
                list.add(doGet(file));
            }
        } else {
            throw new StorageException("listFiles is null", "");
        }
        return list;
    }
}
