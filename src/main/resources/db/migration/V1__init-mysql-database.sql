
    drop table if exists coffee;

    drop table if exists customer;

    create table coffee (
        coffee_style tinyint check ((coffee_style between 0 and 5)),
        price decimal(38,2),
        quantity_on_hand integer,
        version integer,
        created_at datetime(6),
        updated_at datetime(6),
        id varchar(36) not null,
        coffee_name varchar(50) not null,
        upc varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        id integer not null auto_increment,
        version integer,
        created_at datetime(6),
        updated_at datetime(6),
        customer_name varchar(255),
        primary key (id)
    ) engine=InnoDB;
