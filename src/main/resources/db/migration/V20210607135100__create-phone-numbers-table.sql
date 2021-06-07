create table phone_numbers (
id  bigserial not null,
description varchar(255),
number varchar(255),
primary key (id)
);

alter table phone_numbers add constraint UK_PHONE_NUMBER_N unique (number);