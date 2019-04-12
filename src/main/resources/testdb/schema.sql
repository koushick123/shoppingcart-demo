drop table T_CUSTOMER if exists;
drop table T_INVENTORY if exists;
drop table T_ORDER_CHECKOUT if exists;

create table T_CUSTOMER (CUST_ID bigint identity primary key, 
						EMAIL varchar(50) not null,
                        NAME varchar(50) not null);
                        
create table T_INVENTORY (INV_ID bigint identity primary key,
							NAME varchar(50) not null,
							DESCRIPTION varchar(200));
							
create table T_ORDER_CHECKOUT (ORDER_ID bigint identity primary key,
								ORDER_DATE date not null,
								CUST_ID bigint not null,
								INV_ID bigint not null);