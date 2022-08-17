package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class ResumeTestData {
    public static Resume fillSampleResume(String uuid , String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContacts(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContacts(ContactType.SKYPE,"skype:grigory.kislin");
        resume.addContacts(ContactType.MAIL,"gkislin@yandex.ru");
        resume.addContacts(ContactType.LINKEDIN,"https://www.linkedin.com/in/gkislin");
        resume.addContacts(ContactType.GITHUB,"https://github.com/gkislin");
        resume.addContacts(ContactType.STATCKOVERFLOW,"https://stackoverflow.com/users/548473");
        resume.addContacts(ContactType.HOME_PAGE,"http://gkislin.ru/");

        resume.addSections(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSections(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.addSections(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>() {{
            add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
            add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
            add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
            add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
            add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
            add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
            add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        }}));

        resume.addSections(SectionType.QUALIFICATIONS, new ListSection(
            "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
            "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB",
            "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
            "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
            "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
            "Python: Django.",
            "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
            "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
            "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
            "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
            "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
            "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
            "Родной русский, английский \"upper intermediate\""
        ));

        resume.addSections(SectionType.EXPERIENCE, new OrganizationSection(
            (new Organization("Java Online Projects", "http://javaops.ru/",
                    (new Organization.Position(DateUtil.of(2013, Month.MAY), LocalDate.now() ,"Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                    (new Organization.Position(DateUtil.of(2013, Month.APRIL), DateUtil.of(2013, Month.MAY), "СоАвтор другого проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."))
                )),

            (new Organization("Wrike", "https://www.wrike.com/",
                (new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)",  "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))
            )),

            (new Organization("RIT Center", "",
                (new Organization.Position(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"))
            ))
        ));

        resume.addSections(SectionType.EDUCATION, new OrganizationSection(
            (new Organization("Coursera", "https://www.coursera.org/course/progfun",
                (new Organization.Position(
                        DateUtil.of(2013, Month.MARCH),
                        DateUtil.of(2013, Month.MAY),
                        "'Functional Programming Principles in Scala' by Martin Odersky",
                        "")
                )
            )),
            (new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                (new Organization.Position(
                        DateUtil.of(2011, Month.MARCH),
                        DateUtil.of(2011, Month.APRIL),
                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                        "")
                )
            )),
            (new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                (new Organization.Position(
                        DateUtil.of(1993, Month.SEPTEMBER),
                        DateUtil.of(1996, Month.JULY),
                        "Аспирантура (программист С, С++)",
                        "")
                ),
                (new Organization.Position(
                        DateUtil.of(1987, Month.SEPTEMBER),
                        DateUtil.of(1993, Month.JULY),
                        "Инженер (программист Fortran, C)",
                        "")
                )
            )),
            (new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                (new Organization.Position(
                        DateUtil.of(1984, Month.SEPTEMBER),
                        DateUtil.of(1987, Month.JUNE),
                        "Закончил с отличием",
                        "")
                )
            ))
        ));

        return  resume;
    }

    public static void showResume(Resume resume) {
        System.out.println(resume.getUuid());
        System.out.println(resume.getFullName() + "\n");

        for (ContactType contact : ContactType.values()) {
            if (resume.getContact(contact) != null && (("PHONE").equals(resume.getContact(contact))||
                    ("SKYPE").equals(resume.getContact(contact)) ||
                    ("MAIL").equals(resume.getContact(contact))
            )) {
                System.out.println(contact.getTitle() + ":" + resume.getContact(contact));
            }
        }

        for (ContactType contact : ContactType.values()) {
            if (resume.getContact(contact) != null && (!("PHONE").equals(resume.getContact(contact))||
                    !("SKYPE").equals(resume.getContact(contact)) ||
                    !("MAIL").equals(resume.getContact(contact))
            )) {
                System.out.println(contact.getTitle() + " url = " + resume.getContact(contact));
            }
        }
        System.out.print("\n");

        for (SectionType section : SectionType.values()) {
            System.out.println(section.getTitle() + ": \n" + resume.getSection(section));
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        Resume resume = fillSampleResume("uuid1", "Григорий Кислин");
        showResume(resume);
    }
}
