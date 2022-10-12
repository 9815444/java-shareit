INSERT INTO public.users (id, name, email) VALUES (1, 'updateName', 'updateName@user.com');
INSERT INTO public.users (id, name, email) VALUES (4, 'user', 'user@user.com');
INSERT INTO public.users (id, name, email) VALUES (5, 'other', 'other@other.com');

INSERT INTO public.items (id, user_id, name, description, available) VALUES (1, 1, 'Аккумуляторная дрель', 'Аккумуляторная дрель + аккумулятор', true);
INSERT INTO public.items (id, user_id, name, description, available) VALUES (3, 4, 'Клей Момент', 'Тюбик суперклея марки Момент', true);
INSERT INTO public.items (id, user_id, name, description, available) VALUES (2, 4, 'Отвертка', 'Аккумуляторная отвертка', true);

INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (1, 1, 2, '2022-10-11 17:11:52.000000', '2022-10-11 17:11:53.000000', 'APPROVED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (2, 1, 2, '2022-10-12 17:11:49.000000', '2022-10-13 17:11:49.000000', 'APPROVED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (3, 4, 1, '2022-10-12 17:11:51.000000', '2022-10-12 18:11:51.000000', 'REJECTED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (4, 1, 3, '2022-10-11 17:11:58.000000', '2022-10-12 17:11:55.000000', 'REJECTED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (5, 5, 1, '2022-10-21 17:12:00.000000', '2022-10-22 17:12:00.000000', 'APPROVED');

