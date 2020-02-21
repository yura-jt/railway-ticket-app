DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS stations;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS carriages;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS tariffs;

CREATE TABLE users
(
    id           INT(10) NOT NULL AUTO_INCREMENT,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    email        VARCHAR(254) NOT NULL,
    phone_number VARCHAR(20)  NOT NULL,
    password     VARCHAR(130) NOT NULL,
    role_type    VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT users_email_AK UNIQUE (email)
);

CREATE TABLE trains
(
    id   INT(10) NOT NULL AUTO_INCREMENT,
    code VARCHAR(10)  NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE stations
(
    id       INT(10) NOT NULL AUTO_INCREMENT,
    name     VARCHAR(100) NOT NULL,
    type     VARCHAR(20)  NOT NULL,
    time     TIME         NOT NULL,
    distance INT(10) NOT NULL,
    train_id INT(10),
    PRIMARY KEY (id),
    CONSTRAINT stations_trains_FK FOREIGN KEY (train_id) REFERENCES trains (id)
);

CREATE TABLE flights
(
    id             INT(10) NOT NULL AUTO_INCREMENT,
    departure_date DATE NOT NULL,
    train_id       INT(10) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT flights_trains_FK FOREIGN KEY (train_id) REFERENCES trains (id)
);

CREATE TABLE carriages
(
    id        INT(10) NOT NULL AUTO_INCREMENT,
    type      VARCHAR(15) NOT NULL,
    number    INT(2) NOT NULL,
    capacity  INT(3) NOT NULL,
    flight_id INT(10) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT carriages_flights_FK FOREIGN KEY (flight_id) REFERENCES flights (id)
);

CREATE TABLE orders
(
    id                  INT(10) NOT NULL AUTO_INCREMENT,
    departure_station   VARCHAR(100) NOT NULL,
    destination_station VARCHAR(100) NOT NULL,
    departure_date      DATE         NOT NULL,
    from_time           TIME         NOT NULL,
    to_time             TIME         NOT NULL,
    status              VARCHAR(50)  NOT NULL,
    user_id             INT(10) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT orders_users_FK FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE bills
(
    order_id   INT(10) NOT NULL,
    status     VARCHAR(50)             NOT NULL,
    price      DECIMAL                 NOT NULL,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (order_id),
    CONSTRAINT bills_orders_FK FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE seats
(
    bill_id     INT(10) NOT NULL,
    number      INT(2) NOT NULL,
    carriage_id INT(10) NOT NULL,
    status      VARCHAR(20) NOT NULL,
    PRIMARY KEY (bill_id),
    CONSTRAINT seats_bills_FK FOREIGN KEY (bill_id) REFERENCES bills (order_id),
    CONSTRAINT seats_carriages_FK FOREIGN KEY (carriage_id) REFERENCES carriages (id)
);

CREATE TABLE tickets
(
    id                  INT(10) NOT NULL AUTO_INCREMENT,
    departure_station   VARCHAR(100)            NOT NULL,
    destination_station VARCHAR(100)            NOT NULL,
    passenger_name      VARCHAR(100)            NOT NULL,
    price               DECIMAL                 NOT NULL,
    flight_id           INT(10) NOT NULL,
    seat_id             INT(10) NOT NULL,
    user_id             INT(10) NOT NULL,
    bill_id             INT(10) NOT NULL,
    created_on          TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT tickets_seat_id_AK UNIQUE (seat_id),
    CONSTRAINT tickets_flight_id_FK FOREIGN KEY (flight_id) REFERENCES flights (id),
    CONSTRAINT tickets_seat_id_FK FOREIGN KEY (seat_id) REFERENCES seats (bill_id),
    CONSTRAINT tickets_user_id_FK FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT tickets_bill_id_FK FOREIGN KEY (bill_id) REFERENCES bills (order_id)
);

CREATE TABLE tariffs
(
    id            INT(10) NOT NULL AUTO_INCREMENT,
    carriage_type VARCHAR(15) NOT NULL,
    rate          DECIMAL     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT tariffs_carriage_type_AK UNIQUE (carriage_type)
);