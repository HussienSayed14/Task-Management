-- Insert into users table if the email does not exist
INSERT INTO public.users (id, created_at, username, email, password)
VALUES (DEFAULT, '2024-12-08 14:40:31.054', 'Hussien Sayed', 'hussiens399@gmail.com', '$2a$10$3gvuS.hSHbiet8U8Yg/wBuyAiXkWUY8t6Bf8ONPhxtVY3PuoUIZjC')
ON CONFLICT (email) DO NOTHING;


-- Task 1
INSERT INTO public.task (id, due_date, is_deleted, is_overdue, start_date, status, user_id, creation_date, completion_date, update_date, description, title)
VALUES (1, '2024-12-09', false, false, '2024-12-08', 0, 1, '2024-12-08 14:46:58.536', NULL, '2024-12-08 19:22:50.379', 'Task Description', 'Task Title')
On CONFLICT(id) DO NOTHING;


SELECT pg_catalog.setval('public.task_id_seq', COALESCE((SELECT MAX(id) FROM public.task), 0) + 1, false);


