INSERT INTO public.users (id, name, email) VALUES (1, 'updateName', 'updateName@user.com');
INSERT INTO public.users (id, name, email) VALUES (4, 'user', 'user@user.com');
INSERT INTO public.users (id, name, email) VALUES (5, 'other', 'other@other.com');

INSERT INTO public.items (id, user_id, name, description, available) VALUES (1, 1, 'Аккумуляторная дрель', 'Аккумуляторная дрель + аккумулятор', true);
INSERT INTO public.items (id, user_id, name, description, available) VALUES (3, 4, 'Клей Момент', 'Тюбик суперклея марки Момент', true);
INSERT INTO public.items (id, user_id, name, description, available) VALUES (2, 4, 'Отвертка', 'Аккумуляторная отвертка', true);

INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (1, 1, 2, '2022-10-13 11:01:15.000000', '2022-10-13 11:01:16.000000', 'APPROVED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (2, 1, 2, '2022-10-14 11:01:12.000000', '2022-10-15 11:01:12.000000', 'APPROVED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (3, 4, 1, '2022-10-14 11:01:13.000000', '2022-10-14 12:01:13.000000', 'REJECTED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (4, 1, 3, '2022-10-13 11:01:20.000000', '2022-10-14 11:01:17.000000', 'REJECTED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (5, 5, 1, '2022-10-23 11:01:20.000000', '2022-10-24 11:01:20.000000', 'APPROVED');

INSERT INTO public.comments (id, user_id, item_id, text, author_name, created) VALUES (1, 1, 2, 'Add comment from user1', 'updateName', '2022-10-13 11:01:20.564768');

