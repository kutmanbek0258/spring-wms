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

create table wms.write_off
(
    id                 bigserial not null
        constraint write_off_pkey
            primary key,
    reason             smallint
        constraint write_off_reason_check
            check ((reason >= 0) AND (reason <= 2)),
    depot_id           bigint
        constraint fk35ydyavboehd755kuyba418m9
            references wms.depot,
    created_by         varchar(255),
    created_date       timestamp(6),
    last_modified_by   varchar(255),
    last_modified_date timestamp(6)
);

alter table wms.write_off owner to "user";

create table wms.write_off_item
(
    id                 bigserial not null
        constraint write_off_item_pkey
            primary key,
    quantity           integer   not null,
    product_id         bigint
        constraint fkp7jgvevndp95j8ljhkx5b213i
            references wms.product,
    write_off_id       bigint
        constraint fkofmnfvmvdys4iiphhiw1o5c9w
            references wms.write_off,
    write_off_item_id  bigint
        constraint fkennf63kptfdq8s63p1cgywbru
            references wms.write_off,
    created_by         varchar(255),
    created_date       timestamp(6),
    last_modified_by   varchar(255),
    last_modified_date timestamp(6)
);

alter table wms.write_off_item
    owner to "user";

CREATE OR REPLACE FUNCTION wms.update_product_quantity() RETURNS TRIGGER AS
$$
DECLARE
    receipt_sum integer;
    write_off_sum integer;
    totalQuantity integer;
BEGIN

    IF(TG_OP = 'DELETE') THEN
        SELECT SUM(quantity)
        FROM wms.receipt_item
        WHERE "product_id" = OLD."product_id"
        INTO receipt_sum;


        SELECT SUM(quantity)
        FROM wms.write_off_item
        WHERE "product_id" = OLD."product_id"
        INTO write_off_sum;

        IF (receipt_sum IS NULL) THEN
            receipt_sum = 0;
        END IF;

        IF (write_off_sum IS NULL) THEN
            write_off_sum = 0;
        END IF;

        totalQuantity = receipt_sum - write_off_sum;

        IF (write_off_sum > receipt_sum) THEN
            RAISE EXCEPTION USING MESSAGE = CONCAT('the amount of write-offs should not exceed: ', receipt_sum::text, CHR(13),CHR(10), 'current amount: ', write_off_sum::text);
        END IF;

        UPDATE wms.product SET quantity = totalQuantity
        WHERE id = OLD."product_id";

        RETURN OLD;
    ELSE
        SELECT SUM(quantity)
        FROM wms.receipt_item
        WHERE "product_id" = NEW."product_id"
        INTO receipt_sum;


        SELECT SUM(quantity)
        FROM wms.write_off_item
        WHERE "product_id" = NEW."product_id"
        INTO write_off_sum;

        IF (receipt_sum IS NULL) THEN
            receipt_sum = 0;
        END IF;

        IF (write_off_sum IS NULL) THEN
            write_off_sum = 0;
        END IF;

        totalQuantity = receipt_sum - write_off_sum;

        IF (write_off_sum > receipt_sum) THEN
            RAISE EXCEPTION USING MESSAGE = CONCAT('the amount of write-offs should not exceed: ', receipt_sum::text, CHR(13),CHR(10), 'current amount: ', write_off_sum::text);
        END IF;

        UPDATE wms.product SET quantity = totalQuantity
        WHERE id = NEW."product_id";

        RETURN NEW;
    END IF;

END
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_update_write_off
    AFTER INSERT OR UPDATE OR DELETE
    ON wms.write_off_item
    FOR EACH ROW
EXECUTE FUNCTION wms.update_product_quantity();

CREATE TRIGGER check_update_receipt
    AFTER INSERT OR UPDATE OR DELETE
    ON wms.receipt_item
    FOR EACH ROW
EXECUTE FUNCTION wms.update_product_quantity();

CREATE OR REPLACE FUNCTION wms.update_product_cost() RETURNS TRIGGER AS
$$
DECLARE
    last_cost integer;
BEGIN
    IF(TG_OP = 'DELETE') THEN
        SELECT price
        FROM wms.receipt_item
        WHERE "product_id" = OLD."product_id"
        ORDER BY id DESC
        LIMIT 1 INTO last_cost;

        IF NOT FOUND THEN
            RETURN OLD;
        END IF;

        UPDATE wms.product SET cost = last_cost
        WHERE product.id = OLD."product_id";

        RETURN OLD;
    ELSE
        UPDATE wms.product SET cost = NEW.price
        WHERE product.id = NEW."product_id";

        RETURN NEW;
    END IF;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_update_cost
    AFTER INSERT OR UPDATE OR DELETE ON wms.receipt_item
    FOR EACH ROW
EXECUTE FUNCTION wms.update_product_cost();