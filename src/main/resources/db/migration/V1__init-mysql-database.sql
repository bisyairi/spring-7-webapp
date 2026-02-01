
    drop table if exists coffee;

    drop table if exists customer;

    create table coffee (
        id varchar(36) not null,
        version integer,
        coffee_name varchar(50) not null,
        coffee_style tinyint check ((coffee_style between 0 and 5)),
        coffee_detail varchar(255),
        price decimal(38,2),
        quantity_on_hand integer,
        upc varchar(255),
        created_at datetime(6),
        updated_at datetime(6),
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        id integer not null auto_increment,
        version integer,
        customer_name varchar(255),
        created_at datetime(6),
        updated_at datetime(6),
        primary key (id)
    ) engine=InnoDB;
