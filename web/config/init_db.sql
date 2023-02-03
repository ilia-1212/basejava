create table public.resume
(
    uuid      char(36) not null constraint resume_pk primary key,
    full_name text not null
);

create table public.contact
(
    id          serial constraint contact_pk primary key,
    resume_uuid char(36) not null constraint contact_resume_uuid_fk references public.resume on update restrict on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_uuid_type_index
    on public.contact (resume_uuid, type);

create table if not exists public.section
(
    id          serial  constraint section_pk primary key,
    resume_uuid char(36) not null   constraint section_resume_uuid_fk  references public.resume on delete cascade,
    type        text     not null,
    content      text     not null
);

create unique index if not exists section_uuid_type_index
    on public.section (resume_uuid, type);