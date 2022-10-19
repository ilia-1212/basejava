package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface  VoidWithExeption {
    void accept()  throws IOException;
}