package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;
import com.urise.webapp.util.ElementWriter;
import com.urise.webapp.util.ElementProcessor;
import com.urise.webapp.util.ElementReader;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new ObjectOutputStream(os))) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(r.getSections().entrySet(), dos, entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());

                switch (type) {
                    case OBJECTIVE, PERSONAL -> {
                        dos.writeUTF(((TextSection) section).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        writeWithException(((ListSection) section).getItems(), dos, item -> dos.writeUTF(item));

                    }
                    case EXPERIENCE, EDUCATION -> {
                        writeWithException(((OrganizationSection) section).getOrganizations(), dos, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            writeStrNan(org.getHomePage().getUrl(), dos);

                            writeWithException(org.getPositions(), dos, position -> {
                                writeLocalDate(dos, position.getStartDate());
                                writeLocalDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                writeStrNan(position.getDescription(), dos);
                            });
                        });
                    }
                }
            });
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(new ObjectInputStream(is))) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {

        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListSection(readListWithException(dis, () -> dis.readUTF()));
            }
            case EXPERIENCE, EDUCATION -> {
                return new OrganizationSection(
                        readListWithException(dis, () -> new Organization(
                                new Link(dis.readUTF(), readStrNan(dis)),
                                readListWithException(dis, () -> new Organization.Position(
                                                readLocalDate(dis), readLocalDate(dis), dis.readUTF(), readStrNan(dis)
                                        )
                                ))
                        )
                );
            }
            default -> throw new IllegalStateException();
        }
    }

    private void writeStrNan(String str, DataOutputStream dos) throws IOException {
        dos.writeUTF((!(str == null)) ? str : "");
    }

    private String readStrNan(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        if (str.length() == 0) {
            str = null;
        }
        return str;
    }

    private <T> List<T> readListWithException(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();

        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void readWithException(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}