package com.urise.webapp.util;

import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.TextSection;
import junit.framework.TestCase;
import org.junit.Assert;

import static com.urise.webapp.TestData.*;

public class JsonParserTest extends TestCase {

    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume2 = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume2);
    }

    public void testRead() {
    }

    public void testWrite() {
        Section section1 = new TextSection("Objective_1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}