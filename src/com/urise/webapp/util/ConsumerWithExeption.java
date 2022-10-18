package com.urise.webapp.util;

import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerWithExeption extends Consumer {

    @Override
    void accept(Object o);
}
