package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.Resume;
import com.urise.webapp.util.DataParser;

import java.io.*;

public class DataStreamSerializer implements StreamSerializer  {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new ObjectOutputStream(os))) {
            DataParser.write(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream reader = new DataInputStream(new ObjectInputStream(is))) {
            return DataParser.read(reader);
        }
    }

}
