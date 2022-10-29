INSERT INTO public.users (id, name, email) VALUES (1, 'updateName', 'updateName@user.com');
INSERT INTO public.users (id, name, email) VALUES (4, 'user', 'user@user.com');
INSERT INTO public.users (id, name, email) VALUES (5, 'other', 'other@other.com');

INSERT INTO public.items (id, user_id, request_id, name, description, available) VALUES (1, 1, null, 'Аккумуляторная дрель', 'Аккумуляторная дрель + аккумулятор', true);
INSERT INTO public.items (id, user_id, request_id, name, description, available) VALUES (3, 4, null, 'Клей Момент', 'Тюбик суперклея марки Момент', true);
INSERT INTO public.items (id, user_id, request_id, name, description, available) VALUES (2, 4, null, 'Отвертка', 'Аккумуляторная отвертка', true);


INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (1, 1, 2, '2022-10-22 16:06:23.000000', '2022-10-22 16:06:24.000000', 'APPROVED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (2, 1, 2, '2022-10-23 16:06:20.000000', '2022-10-24 16:06:20.000000', 'APPROVED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (3, 4, 1, '2022-10-23 16:06:21.000000', '2022-10-23 17:06:21.000000', 'REJECTED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (4, 1, 3, '2022-10-22 16:06:28.000000', '2022-10-23 16:06:25.000000', 'REJECTED');
INSERT INTO public.booking (id, user_id, item_id, booking_start, booking_end, status) VALUES (5, 5, 1, '2022-11-01 16:06:31.000000', '2022-11-02 16:06:31.000000', 'APPROVED');


INSERT INTO public.comments (id, user_id, item_id, text, author_name, created) VALUES (1, 1, 2, 'Add comment from user1', 'updateName', '2022-10-22 16:06:31.654834');
