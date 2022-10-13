-- drop table users cascade;
-- drop table items cascade;
-- drop table booking cascade;
-- drop table comments cascade;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(255) NOT NULL,
                                     email VARCHAR(512) NOT NULL,
                                     CONSTRAINT pk_user PRIMARY KEY (id),
                                     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS items (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     user_id BIGINT NOT NULL REFERENCES users (id),
                                     name VARCHAR(255) NOT NULL,
                                     description VARCHAR(512) NOT NULL,
                                     available BOOLEAN,
                                     CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS booking (
                                       id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                       user_id BIGINT NOT NULL REFERENCES users (id),
                                       item_id BIGINT NOT NULL REFERENCES items (id),
                                       booking_start timestamp NOT NULL,
                                       booking_end timestamp NOT NULL,
                                       status VARCHAR(255) NOT NULL,
                                       CONSTRAINT pk_booking PRIMARY KEY (id),
                                       constraint st_date check ( booking_start >= current_date ),
                                       constraint en_date check ( booking.booking_end >= current_date ),
                                       constraint st_en_date check ( booking.booking_end >= booking.booking_start )
);

CREATE TABLE IF NOT EXISTS comments (
                                       id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                       user_id BIGINT NOT NULL REFERENCES users (id),
                                       item_id BIGINT NOT NULL REFERENCES items (id),
                                       text VARCHAR NOT NULL,
                                       author_name varchar not null,
                                       created timestamp NOT NULL,
                                       CONSTRAINT pk_comments PRIMARY KEY (id)

);

