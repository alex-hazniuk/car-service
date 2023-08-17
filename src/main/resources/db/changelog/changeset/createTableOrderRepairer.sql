create table order_repairer (
    order_id    bigint,
    repairer_id int,
    primary key (order_id, repairer_id),
    foreign key (order_id) references orders(id),
    foreign key (repairer_id) references repairer(id)
);