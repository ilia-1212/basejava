package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;

public class ResumeTestData {
    public static Resume fillSampleResume(String uuid , String fullName) {
        Resume resume = new Resume(uuid, fullName);

        EnumMap<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
        contactMap.put(ContactType.PHONE, "+7(921) 855-0482");
        contactMap.put(ContactType.SKYPE,"skype:grigory.kislin");
        contactMap.put(ContactType.MAIL,"gkislin@yandex.ru");
        contactMap.put(ContactType.LINKEDIN,"https://www.linkedin.com/in/gkislin");
        contactMap.put(ContactType.GITHUB,"https://github.com/gkislin");
        contactMap.put(ContactType.STATCKOVERFLOW,"https://stackoverflow.com/users/548473");
        contactMap.put(ContactType.HOME_PAGE,"http://gkislin.ru/");

        resume.setContacts(contactMap);

        EnumMap<SectionType, Section> sectionMap = new EnumMap<>(SectionType.class);
        sectionMap.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sectionMap.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        sectionMap.put(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>() {{
            add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
            add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
            add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
            add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
            add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
            add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
            add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        }}));

        sectionMap.put(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>() {{
            add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
            add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
            add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
            add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
            add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
            add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
            add("Python: Django.");
            add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
            add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
            add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
            add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
            add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
            add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
            add("Родной русский, английский \"upper intermediate\"");
        }}));

        sectionMap.put(SectionType.EXPERIENCE, new OrganizationSection(new ArrayList<>() {{
            add(new Organization("Java Online Projects", "http://javaops.ru/", new ArrayList<>(){{
                add(new Position("Автор проекта.", DateUtil.of(2013, Month.MAY), LocalDate.now(), "Создание, организация и проведение Java онлайн проектов и стажировок."));
                add(new Position("СоАвтор другого проекта.", DateUtil.of(2013, Month.APRIL), DateUtil.of(2013, Month.MAY), "Создание, организация и проведение Java онлайн проектов и стажировок."));
            }}));

            add(new Organization("Wrike", "https://www.wrike.com/", new ArrayList<>(){{
                add(new Position("Старший разработчик (backend)", DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
            }}));
            add(new Organization("RIT Center", "", new ArrayList<>(){{
                add(new Position("Java архитектор", DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
            }}));
            add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", new ArrayList<>(){{
                add(new Position("Ведущий программист", DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2014, Month.DECEMBER), "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
            }}));
            add(new Organization("Yota", "https://www.yota.ru/", new ArrayList<>(){{
                add(new Position("Ведущий специалист", DateUtil.of(2008, Month.JUNE), DateUtil.of(2012, Month.OCTOBER), "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
            }}));
            add(new Organization("Enkata", "http://enkata.com/", new ArrayList<>(){{
                add(new Position("Разработчик ПО", DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JUNE), "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
            }}));
            add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html", new ArrayList<>(){{
                add(new Position("Разработчик ПО", DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY), "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
            }}));
            add(new Organization("Alcatel", "http://enkata.com/", new ArrayList<>(){{
                add(new Position("Инженер по аппаратному и программному тестированию", DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY), "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
            }}));
        }}));
        sectionMap.put(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>() {{
            add(new Organization("Coursera", "https://www.coursera.org/course/progfun", new ArrayList<>(){{
                add(new Position("'Functional Programming Principles in Scala' by Martin Odersky",
                        DateUtil.of(2013, Month.MARCH),
                        DateUtil.of(2013, Month.MAY),
                        "")
                );
            }}));
            add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", new ArrayList<>(){{
                add(new Position("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                        DateUtil.of(2011, Month.MARCH),
                        DateUtil.of(2011, Month.APRIL),
                        "")
                );
            }}));
            add(new Organization("Siemens AG", "http://www.siemens.ru/", new ArrayList<>(){{
                add(new Position("3 месяца обучения мобильным IN сетям (Берлин)",
                        DateUtil.of(2005, Month.JANUARY),
                        DateUtil.of(2005, Month.APRIL),
                        "")
                );
            }}));
            add(new Organization("Alcatel", "http://www.alcatel.ru/", new ArrayList<>(){{
                add(new Position("6 месяцев обучения цифровым телефонным сетям (Москва)",
                        DateUtil.of(1997, Month.SEPTEMBER),
                        DateUtil.of(1998, Month.MARCH),
                        "")
                );
            }}));
            add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", new ArrayList<>(){{
                add(new Position("Аспирантура (программист С, С++)",
                        DateUtil.of(1993, Month.SEPTEMBER),
                        DateUtil.of(1996, Month.JULY),
                        "")
                );
                add(new Position("Инженер (программист Fortran, C)",
                        DateUtil.of(1987, Month.SEPTEMBER),
                        DateUtil.of(1993, Month.JULY),
                        "")
                );
            }}));
            add(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/", new ArrayList<>(){{
                add(new Position("Закончил с отличием",
                        DateUtil.of(1984, Month.SEPTEMBER),
                        DateUtil.of(1987, Month.JUNE),
                        "")
                );
            }}));
        }}));
        resume.setSections(sectionMap);
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
