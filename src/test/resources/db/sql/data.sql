INSERT INTO users (first_name, last_name, email, phone_number, password, role_type)
VALUES ('John', 'McClane', 'bruce@gmail.com', '+380991234567', 'passwordT1', 'PASSENGER'),
       ('Jobe', 'Smith', 'lawnmower@gmail.com', '+380509998877', 'passwordT2', 'ADMIN'),
       ('Kaleb', 'Jordan', 'dr.noble@gmail.com', '+43125333564', 'admin', 'ADMIN');

INSERT INTO trains (code, name)
VALUES ('43K', 'Подільський експрес'),
       ('122', 'Срібна стріла');

INSERT INTO stations (name, type, time, distance, train_id)
VALUES ('Київ', 'DEPARTURE_STATION', '18:55:00', 0, 1),
       ('Львів', 'ARRIVING_STATION', '02:34:00', 400, 1),
       ('Львів', 'DEPARTURE_STATION', '02:59:00', 400, 1),
       ('Івано-Франківськ', 'ARRIVING_STATION', '05:50:00', 700, 1),
       ('Київ', 'DEPARTURE_STATION', '09:20:00', 0, 2),
       ('Кривий Ріг', 'ARRIVING_STATION', '15:30:00', 335, 2),
       ('Кривий Ріг', 'DEPARTURE_STATION', '15:35:00', 335, 2),
       ('Одеса', 'ARRIVING_STATION', '19:10:00', 554, 2);

INSERT INTO orders (departure_station, destination_station, departure_date,
                    from_time, to_time, status, user_id)
VALUES ('Київ', 'Івано-Франківськ', '2019-03-01', '00:00:00', '23:59:00', 'WAITING_FOR_PAYMENT', 1),
       ('Київ', 'Кривий Ріг', '2019-03-02', '05:00:00', '12:00:00', 'DONE', 2),
       ('Київ', 'Кривий Ріг', '2019-03-02', '05:00:00', '12:00:00', 'DONE', 3);

INSERT INTO flights (departure_date, train_id)
VALUES ('2019-03-01', 1),
       ('2019-03-01', 2),
       ('2019-03-02', 2),
       ('2019-03-03', 1);

INSERT INTO carriages (type, number, capacity, flight_id)
VALUES ('КУПЕ', 1, 36, 1),
       ('КУПЕ', 3, 36, 1),
       ('КУПЕ', 7, 36, 1),
       ('ПЛАЦКАРТ', 4, 58, 1),
       ('ПЛАЦКАРТ', 5, 58, 2),
       ('ПЛАЦКАРТ', 8, 58, 2),
       ('КУПЕ', 1, 2, 2),
       ('КУПЕ', 2, 2, 2);

INSERT INTO bills (order_id, status, price)
VALUES (1, 'INVOICED', 860),
       (2, 'PAID', 700),
       (3, 'PAID', 700);

INSERT INTO seats (bill_id, number, carriage_id, status)
VALUES (1, 1, 1, 'RESERVED'),
       (2, 1, 6, 'SOLD'),
       (3, 2, 6, 'SOLD');

INSERT INTO tickets (departure_station, destination_station, passenger_name, price, flight_id,
                     seat_id, user_id, bill_id, created_on)
VALUES ('Кривий Ріг', 'Одеса', 'Бундуцький Олексій', 599.60, 2, 1, 1, 1, default),
       ('Кривий Ріг', 'Одеса', 'Бундуцька Марія', 599.60, 2, 2, 1, 2, default);

INSERT INTO tariffs (carriage_type, rate)
VALUES ('OPEN_SLEEPING', 10),
       ('COUPE', 24),
       ('LUX', 57);