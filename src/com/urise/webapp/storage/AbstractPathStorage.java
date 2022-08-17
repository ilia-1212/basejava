package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected abstract void doWrite(OutputStream os, Resume r) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    public void clear() {
        try {
           Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Directory delete error", null);
        }
    }

    @Override
    public int size() throws IOException {
        return Files.list(directory).toList().size();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            doWrite(new BufferedOutputStream(Files.newOutputStream(path, LinkOption.NOFOLLOW_LINKS)), r);
        } catch (IOException e) {
            throw new StorageException("Path write error "  + path.toAbsolutePath().toString(), r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path.toAbsolutePath().toString(), r.getUuid(), e);
        }
        doUpdate(path, r);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream((Files.newInputStream(path, LinkOption.NOFOLLOW_LINKS))));
        } catch (IOException e) {
            throw new StorageException("Path read error " + path.toAbsolutePath().toString(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error " + path.toAbsolutePath().toString(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listResume = new ArrayList<>();

        try {
            Files.list(directory).forEach(x -> listResume.add(doGet(x)));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return listResume;
    }
}
