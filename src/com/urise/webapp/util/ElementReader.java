package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface ElementReader<T> {
    T read() throws IOException;
}