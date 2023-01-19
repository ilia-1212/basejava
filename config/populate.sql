insert into resume(uuid, full_name) values
    ('5de85d34-afd7-4eca-9079-48b91bb1b641','Андрей Суперджетов'),
    ('9d7ab448-bd25-4277-914e-d20ca348c105','Дима Боингов'),
    ('b2bf9ade-8afc-4596-9f01-282d8f743af6','Илья Эирбасов'),
    ('b2bf9ade-8afc-4596-9f01-282d8f743af7','Илья Вертолетов');

insert into contact(resume_uuid, type, value) values
    ('5de85d34-afd7-4eca-9079-48b91bb1b641', 'MAIL', 'mail1@ya.ru'),
    ('5de85d34-afd7-4eca-9079-48b91bb1b641', 'PHONE', '11111'),
    ('b2bf9ade-8afc-4596-9f01-282d8f743af7', 'MAIL', 'mail4@ya.ru'),
    ('b2bf9ade-8afc-4596-9f01-282d8f743af7', 'PHONE', '444');

insert into section(resume_uuid, type, value) values
  ('5de85d34-afd7-4eca-9079-48b91bb1b641', 'OBJECTIVE', 'Objective1'),
  ('5de85d34-afd7-4eca-9079-48b91bb1b641', 'PERSONAL', 'Personal data'),
  ('5de85d34-afd7-4eca-9079-48b91bb1b641', 'ACHIEVEMENT', 'Achivment11\nAchivment12\nAchivment13'),
  ('5de85d34-afd7-4eca-9079-48b91bb1b641', 'QUALIFICATIONS', 'Java\nSQL\nJavaScript'),
  ('9d7ab448-bd25-4277-914e-d20ca348c105', 'OBJECTIVE', 'OBJECTIVE_2'),
  ('9d7ab448-bd25-4277-914e-d20ca348c105', 'PERSONAL', 'Personal data_2');