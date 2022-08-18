package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private Serializer serializer;

    public PathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    public PathStorage(String dir,Serializer serializer) {
        this(dir);
        setSerializer(serializer);
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void clear() {
        try {
           getFiles().forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Directory delete error", null);
        }
    }

    @Override
    public int size() throws IOException {
        return getFiles().toList().size();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            serializer.doWrite(new BufferedOutputStream(Files.newOutputStream(path, LinkOption.NOFOLLOW_LINKS)), r);
        } catch (IOException e) {
            throw new StorageException("Path write error "  + path.toAbsolutePath(), r.getUuid(), e);
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
            throw new StorageException("Couldn't create path " + path.toAbsolutePath(), r.getUuid(), e);
        }
        doUpdate(path, r);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream((Files.newInputStream(path, LinkOption.NOFOLLOW_LINKS))));
        } catch (IOException e) {
            throw new StorageException("Path read error " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listResume = new ArrayList<>();
        try {
            getFiles().forEach(x -> listResume.add(doGet(x)));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return listResume;
    }

    private Stream<Path> getFiles() throws IOException {
        return Files.list(directory);
    }

}
