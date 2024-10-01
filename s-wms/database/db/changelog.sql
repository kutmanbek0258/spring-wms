create table if not exists wms.company
(
    id bigserial not null
    constraint company_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    address varchar(1024),
    description varchar(10000),
    email varchar(255),
    name varchar(1024),
    phone varchar(100)
    );

create table if not exists wms.person
(
    id bigserial not null
    constraint person_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    address varchar(1024),
    email varchar(255),
    full_name varchar(1024),
    phone varchar(1024)
    );

alter table wms.person owner to "user";

create table if not exists wms.depot
(
    id bigserial not null
    constraint depot_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    address varchar(1024),
    description varchar(1024),
    name varchar(100),
    company_id bigint
    constraint fk107b0lcwhugl36itm3y2b0hm6
    references wms.company,
    manager_id bigint
    constraint fkafbmhnbrowyg9gcc0723c41mt
    references wms.person
    );

alter table wms.depot owner to "user";

create table if not exists wms.price_template
(
    id bigserial not null
    constraint price_template_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    description varchar(255),
    formula double precision not null,
    name varchar(1024)
    );

alter table wms.price_template owner to "user";

create table if not exists wms.product_group
(
    id bigserial not null
    constraint product_group_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    description varchar(255),
    name varchar(256)
    );

alter table wms.product_group owner to "user";

create table if not exists wms.product
(
    id bigserial not null
    constraint product_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    barcode varchar(255),
    cost double precision,
    created_at timestamp(6),
    description varchar(255),
    name varchar(255),
    price double precision,
    quantity double precision,
    updated_at timestamp(6),
    price_template_id bigint
    constraint fkauxpx6f5r243p5q58eg0h3yoh
    references wms.price_template,
    product_group_id bigint
    constraint fkd1puiblqvkggoc63q7c3ux5x6
    references wms.product_group
    );

alter table wms.product owner to "user";

create table if not exists wms.samlesman
(
    id bigserial not null
    constraint samlesman_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    company_id bigint
    constraint fk9klint0w6x7g63fvq3nik2xsu
    references wms.company,
    person_id bigint
    constraint fk3ugi4xmvr40vxkqt7kwsoldmg
    references wms.person
    );

alter table wms.samlesman owner to "user";

create table if not exists wms.supplier
(
    id bigserial not null
    constraint supplier_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    company_id bigint
    constraint fk9t6j7witd0y83ckoat5q1m7wa
    references wms.company,
    person_id bigint
    constraint fkp60wb4i7fff9fomxg28uxl4e2
    references wms.person
    );

alter table wms.supplier owner to "user";

create table if not exists wms.receipt
(
    id bigserial not null
    constraint receipt_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    depot_id bigint
    constraint fkd3bvldf5m28k429unsu469n7j
    references wms.depot,
    supplier_id bigint
    constraint fk76be0gur5257gn4ocs942s9ml
    references wms.supplier
    );

alter table wms.receipt owner to "user";

create table if not exists wms.receipt_item
(
    id bigserial not null
    constraint receipt_item_pkey
    primary key,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    price double precision,
    quantity double precision,
    product_id bigint
    constraint fkadhg4s6y4syjwnexabmyxkuyn
    references wms.product,
    receipt_id bigint
    constraint fksohgmt8ntavcgj10ha2duc8la
    references wms.receipt
    );

alter table wms.receipt_item owner to "user";

