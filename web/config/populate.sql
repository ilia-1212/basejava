insert into resume(uuid, full_name) values
    ('fabf4f0f-272f-41f5-b79f-3f5c1aae8cd9','Андрей Суперджетов'),
    ('13cd2aec-b0de-432a-8528-958a96bc6cba','Дима Боингов'),
    ('946e7dcd-a050-4556-b946-54975a6d5c14','Илья Эирбасов'),
    ('b2bf9ade-8afc-4596-9f01-282d8f743af7','Илья Вертолетов');

insert into contact(resume_uuid, type, value) values
    ('fabf4f0f-272f-41f5-b79f-3f5c1aae8cd9', 'MAIL', 'mail1@ya.ru'),
    ('fabf4f0f-272f-41f5-b79f-3f5c1aae8cd9', 'PHONE', '11111'),
    ('13cd2aec-b0de-432a-8528-958a96bc6cba', 'MAIL', 'mail4@ya.ru'),
    ('13cd2aec-b0de-432a-8528-958a96bc6cba', 'PHONE', '444');

insert into section(resume_uuid, type, content) values
  ('fabf4f0f-272f-41f5-b79f-3f5c1aae8cd9', 'OBJECTIVE', '{
  "CLASSNAME": "com.urise.webapp.model.TextSection",
  "INSTANCE": {
    "content": "Objective1"
  }
}'),
  ('fabf4f0f-272f-41f5-b79f-3f5c1aae8cd9', 'PERSONAL', '{
  "CLASSNAME": "com.urise.webapp.model.TextSection",
  "INSTANCE": {
    "content": "Personal data"
  }
}'),
  ('fabf4f0f-272f-41f5-b79f-3f5c1aae8cd9', 'ACHIEVEMENT', '{
  "CLASSNAME": "com.urise.webapp.model.ListSection",
  "INSTANCE": {
    "items": [
      "Achivment11",
      "Achivment12",
      "Achivment13"
    ]
  }
}'),
  ('fabf4f0f-272f-41f5-b79f-3f5c1aae8cd9', 'QUALIFICATIONS', '{
  "CLASSNAME": "com.urise.webapp.model.ListSection",
  "INSTANCE": {
    "items": [
      "Java",
      "SQL",
      "JavaScript"
    ]
  }
}'),
  ('946e7dcd-a050-4556-b946-54975a6d5c14', 'OBJECTIVE', '{
  "CLASSNAME": "com.urise.webapp.model.TextSection",
  "INSTANCE": {
    "content": "Objective 2"
  }
}'),
  ('946e7dcd-a050-4556-b946-54975a6d5c14', 'PERSONAL', '{
  "CLASSNAME": "com.urise.webapp.model.TextSection",
  "INSTANCE": {
    "content": "Personal data 2"
  }
}');