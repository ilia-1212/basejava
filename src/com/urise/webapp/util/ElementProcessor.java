package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface ElementProcessor {
    void process()  throws IOException;
}
