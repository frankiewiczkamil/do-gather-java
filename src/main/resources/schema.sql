create table task_list
(
    id          UUID primary key,
    name        varchar(50) not null,
    description varchar(255),
    created_at  timestamp default current_timestamp,
    creator_id  UUID
);

create table task
(
    id           UUID primary key,
    task_list_id UUID        not null,
    name         varchar(50) not null,
    description  varchar(255),
    created_at   timestamp default current_timestamp,
    time_spent   int,
    state        varchar(50),
    progress     int,
    creator_id   UUID
);

create table permission
(
    id             UUID primary key,
    participant_id UUID        not null,
    task_list_id   UUID        not null,
    role           varchar(50) not null,
    created_at     timestamp default current_timestamp
);