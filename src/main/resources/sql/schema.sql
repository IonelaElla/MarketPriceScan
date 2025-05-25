-- products: id, product_id, product_name, product_category, brand, package_quantity, package_unit
-- stores: id, name
-- prices: id, store_id, product_id, price, currency, effective_date
-- discounts: id, store_id, product_id, from_date, to_date, percentage_of_discount

create table products (
	id bigserial not null primary key,
	product_id varchar(255) not null,
	product_name varchar(255) not null,
	product_category varchar(255) not null,
	brand varchar(255) not null,
	package_quantity decimal(15,3) not null,
	package_unit varchar(255) not null,
	unique (product_id)
);
create table stores (
	id bigserial not null primary key,
	name varchar(255) not null
);
create table prices (
	id bigserial not null primary key,
	store_id bigint not null,
	product_id bigint not null,
	price decimal(15,3) not null,
	currency varchar(255) not null default 'RON',
	effective_date date not null,
	foreign key (store_id) references stores(id),
	foreign key (product_id) references products(id),
	unique (store_id, product_id, effective_date)
);
create table discounts (
	id bigserial not null primary key,
	store_id bigint not null,
	product_id bigint not null,
	percentage_of_discount int not null,
	from_date date not null,
	to_date date not null,
	foreign key (store_id) references stores(id),
	foreign key (product_id) references products(id)
);
