package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface SupplierWithExeption<T> {
    T get() throws IOException;
}