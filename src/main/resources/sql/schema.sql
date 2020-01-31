DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS routes;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS carriages;
DROP TABLE IF EXISTS seats;

CREATE TABLE users
(
    id           INT(10)      NOT NULL AUTO_INCREMENT,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    email        VARCHAR(254) NOT NULL UNIQUE,
    phone_number VARCHAR(20)  NULL,
    password     VARCHAR(20)  NOT NULL,
    role_type    VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tickets
(
    id                  INT(10)                 NOT NULL AUTO_INCREMENT,
    departure_station   VARCHAR(100)            NOT NULL,
    destination_station VARCHAR(100)            NOT NULL,
    passenger_name      VARCHAR(100)            NOT NULL,
    price               DECIMAL                 NOT NULL,
    flight_id           INT(10)                 NOT NULL,
    seat_id             INT(10)                 NOT NULL,
    user_id             INT(10)                 NOT NULL,
    order_id            INT(10)                 NOT NULL,
    created_on          TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE trains
(
    id       INT(10)      NOT NULL AUTO_INCREMENT,
    code     VARCHAR(10)  NOT NULL,
    name     VARCHAR(100) NOT NULL,
    route_id INT(10)      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE routes
(
    train_id     INT(10),
    station      VARCHAR(100) NOT NULL,
    station_type VARCHAR(20)  NOT NULL,
    station_time TIMESTAMP    NOT NULL,
    distance     INT(10)      NOT NULL,
    PRIMARY KEY (train_id),
    CONSTRAINT routes_trains_fk FOREIGN KEY (train_id) REFERENCES trains (id)
);

CREATE TABLE flights
(
    id             INT(10) NOT NULL AUTO_INCREMENT,
    departure_date DATE    NOT NULL,
    train_id       INT(10) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT flights_trains_fk FOREIGN KEY (train_id) REFERENCES trains (id)
);

CREATE TABLE carriages
(
    id        INT(10)     NOT NULL AUTO_INCREMENT,
    type      VARCHAR(15) NOT NULL,
    number    INT(2)      NOT NULL,
    flight_id INT(10)     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT carriages_flights_fk FOREIGN KEY (flight_id) REFERENCES flights (id)
);

CREATE TABLE seats
(
    id          INT(10)     NOT NULL AUTO_INCREMENT,
    carriage_id INT(10)     NOT NULL,
    ticket_id   INT(10)     NULL,
    status      VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT seats_ticket_fk FOREIGN KEY (ticket_id) REFERENCES tickets (id)
);

CREATE TABLE orders
(
    id                  INT(10)      NOT NULL AUTO_INCREMENT,
    departure_station   VARCHAR(100) NOT NULL,
    destination_station VARCHAR(100) NOT NULL,
    departure_date      DATE         NOT NULL,
    from_time           TIMESTAMP    NOT NULL,
    to_time             TIMESTAMP    NOT NULL,
    status              VARCHAR(50)  NOT NULL,
    user_id             INT(10)      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT orders_users_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE bills
(
    order_id   INT(10)                 NOT NULL AUTO_INCREMENT,
    status     VARCHAR(50)             NOT NULL,
    price      DECIMAL                 NOT NULL,
    user_id    INT(10)                 NOT NULL,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (order_id),
    CONSTRAINT bills_orders_fk FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE tariffs
(
    id        INT(10)     NOT NULL AUTO_INCREMENT,
    seat_type VARCHAR(15) NOT NULL,
    rate      DECIMAL     NOT NULL,
    PRIMARY KEY (id)
);