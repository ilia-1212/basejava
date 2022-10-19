package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithExeption<T> {
    void accept(T t) throws IOException;
}