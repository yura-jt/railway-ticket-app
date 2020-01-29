INSERT INTO railway.users(first_name, last_name, email, phone_number, password, role_type)
VALUES ('John', 'McClane', 'bruce@gmail.com', '+380991234567', 'passwordT1', 'passenger'),
       ('Jobe', 'Smith', 'lawnmower@gmail.com', '+380509998877', 'passwordT2', 'admin');

INSERT INTO railway.tickets(departure_station, destination_station, passenger_name, price, flight_id, seat_id, user_id,
                            order_id, created_on)
VALUES ('Київ', 'Львів', 'Бундуцький Олексій', 599.60, 1, 2, 1, 1, default),
       ('Київ', 'Львів', 'Бундуцька Марія', 599.60, 1, 3, 1, 2, default);

INSERT INTO railway.orders(status, user_id, seat_id)
VALUES ('ACCEPTED', 1, 1),
       ('PAID', 1, 2);

INSERT INTO railway.trains(code, name, departure_station, destination_station, departure_time, arrive_time)
VALUES ('43K', 'Подільський експрес', 'Київ', 'Івано-Франківськ', '18:55:00', '06:51:00'),
       ('122', 'Срібна стріла', 'Київ', 'Одеса', '20:34:00', '12:45:00');

INSERT INTO railway.flights(departure_date, train_id)
VALUES ('2019-02-01', 1),
       ('2019-03-10', 2);

INSERT INTO railway.carriages(type, number, flight_id)
VALUES ('КУПЕ', 7, 1),
       ('ПЛАЦКАРТ', 2, 2);

INSERT INTO railway.seats(carriage_id, ticket_id, status)
VALUES (1, NULL, 'RESERVED'),
       (1, 2, 'SOLD');