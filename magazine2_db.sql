CREATE DATABASE magazine2_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

---------
	
CREATE TABLE IF NOT EXISTS public.product
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    title character varying(100) COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_title_key UNIQUE (title)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;

---------

INSERT INTO public.product (title,price) VALUES
('Сыр', 250),
('Селедка', 500),
('Лимонад', 55),
('Молоко', 59),
('Кефир', 60),
('Огурчики', 100),
('Хлеб', 35),
('Сметана', 85),
('Оленина', 2500),
('Йогурт', 55),
('Мандарины', 200),
('Макароны', 70),
('Гречка', 95),
('Квас', 75),
('Сливки', 100),
('Пончики', 65),
('Конфеты', 600),
('Зефир', 150),
('Мармелад', 500),
('Капуста', 5),
('Крекер', 54),
('Манка', 75),
('Булгур', 95),
('Сахар', 107),
('Свинина', 350);

---------
CREATE TABLE IF NOT EXISTS public.roles
(
    id integer NOT NULL DEFAULT nextval('roles_id_seq'::regclass),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.roles
    OWNER to postgres;
---------
CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    username character varying(30) COLLATE pg_catalog."default" NOT NULL,
    password character varying(80) COLLATE pg_catalog."default" NOT NULL,
    email character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_username_key UNIQUE (username)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;
---------

CREATE TABLE IF NOT EXISTS public.users_roles
(
    user_id bigint NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users_roles
    OWNER to postgres;

---------
INSERT INTO public.users (username,password,email) VALUES
                                             ('user',''$2y$10$162tSbzIJB.XnyKriLz7F.YJ9Lrzq5MKKCN7TlLwfQYcfWoj4g4Cu', 'user@gmail.com'),
                                             ('admin',''$2y$10$162tSbzIJB.XnyKriLz7F.YJ9Lrzq5MKKCN7TlLwfQYcfWoj4g4Cu', 'admin@gmail.com'),
                                             ('manager',''$2y$10$162tSbzIJB.XnyKriLz7F.YJ9Lrzq5MKKCN7TlLwfQYcfWoj4g4Cu', 'manager@gmail.com'),
                                             ('root',''$2y$10$162tSbzIJB.XnyKriLz7F.YJ9Lrzq5MKKCN7TlLwfQYcfWoj4g4Cu', 'root@gmail.com');
---------
INSERT INTO public.roles (name) VALUES ('ROLE_USER'),
                                       ('ROLE_ADMIN'),
                                       ('ROLE_MANAGER'),
                                       ('ROLE_ROOT');
---------
INSERT INTO public.users_roles (user_id,role_id) VALUES (1,1),
                                                 VALUES (2,2),
                                                 VALUES (2,3),
                                                 VALUES (3,3),
                                                 VALUES (4,2),
                                                 VALUES (4,3),
                                                 VALUES (4,4);
