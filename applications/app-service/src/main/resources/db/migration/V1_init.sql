CREATE TABLE public.rol (
	rol_id serial4 NOT NULL,
	nombre varchar(50) NOT NULL,
	descripcion varchar(200) NULL,
	CONSTRAINT rol_nombre_key UNIQUE (nombre),
	CONSTRAINT rol_pkey PRIMARY KEY (rol_id)
);

CREATE TABLE public.users (
	user_id uuid DEFAULT gen_random_uuid() NOT NULL,
	document_id varchar(50) NOT NULL,
	"name" varchar(100) NOT NULL,
	lastname varchar(100) NOT NULL,
	birth_date date NULL,
	address varchar(255) NULL,
	email varchar(150) NULL,
	phone varchar(50) NULL,
	base_salary numeric(15, 2) NULL,
	password_hash varchar(100) NOT NULL,
	id_rol int4 NOT NULL,
	CONSTRAINT users_email_key UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

ALTER TABLE public.users ADD CONSTRAINT fk_user_rol FOREIGN KEY (id_rol) REFERENCES public.rol(rol_id);

INSERT INTO public.rol
(rol_id, nombre, descripcion)
VALUES(1, 'ADMIN', 'Administrador del sistema');
INSERT INTO public.rol
(rol_id, nombre, descripcion)
VALUES(2, 'ASESOR', 'Asesor comercial');
INSERT INTO public.rol
(rol_id, nombre, descripcion)
VALUES(3, 'CLIENTE', 'Usuario cliente');

INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('55773621-3ecd-4238-8222-3442ec22d91a'::uuid, '1234', 'Carlos', 'Ramírez', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'carlos.ramirez@example.org', '+573155667788', 3200000.00, '1234', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('26b2c31c-855f-4977-9290-f3d6f85bdc8e'::uuid, '1235', 'Carlos', 'Ramírez', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'carlos.ra@example.org', '+573155667788', 3200000.00, '1235', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('de72dcb7-3b75-48f5-8c02-8c21117564b5'::uuid, '1236', 'Carlos', 'Ramírez', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'carlos.ram@example.org', '+573155667788', 3200000.00, '1236', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('5ac91c47-096c-4bd3-88ca-a0128fb284c2'::uuid, '12345', 'Carlos', 'Ramírez', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'carloss.ram@example.org', '+573155667788', 3200000.00, '12345', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('3dd30a7c-642a-44e2-aa37-4a9835287208'::uuid, '1245', 'Carlos', 'Lopera', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'cars.ram@example.org', '+573155667788', 3200000.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('b361ee84-ba4f-49f0-9a6c-8dabe41c4df6'::uuid, '1245', 'Carlos', 'Lope', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'cars.lo@example.org', '+573155667788', 3200000.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('62bb4355-7e7c-4843-808b-b659fb7c7804'::uuid, '1245', 'Carlos', 'Rami', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'cars.lop@example.org', '+573155667788', 3200000.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('3269a44f-7c7b-4f86-add4-eeaa51a8ba4e'::uuid, '1245', 'Carlos', 'Rami', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'ca.lop@example.org', '4444', 3200000.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('69b261a2-55ec-43d3-af57-e617634560e2'::uuid, '1245', 'Carlos', 'Rami', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'lop@example.org', '<*573155667788', 3200000.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('cf10c02c-a91b-40bd-9bed-fd9f894c49d6'::uuid, '1245', 'Carlos', 'Rami', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'lopez@example.org', '+573155667788', 15000001.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('bf2b15e7-f47c-4127-85ed-2275577755a9'::uuid, '1245', 'Carlos', 'Rami', '1988-12-15', 'Carrera 45 #67-89, Bogotá', 'lope26z@example.org', '+573155667788', 15000000.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('37c1cc82-2186-4645-9338-7f260ed299c2'::uuid, '1245', 'Carlos', 'Rami', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lope266z@example.org', '+5731556677888888', 15000000.00, '1245', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('e5f89339-9178-442e-840d-0ebc4294c7f6'::uuid, '12333', 'Carlos', 'Rami', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lope3266z@example.org', '+5731556677888888', 15000000.00, '12333', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('a85adbcd-b727-441d-9589-b64f9f0f97da'::uuid, '12333', 'Carlos', 'Rami', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lope326w6z@example.org', '+5731556677888888', 15000000.00, '12333', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('5e2686cb-8efe-48d1-bac1-814fd2fd4cad'::uuid, '12333', 'Carlos', 'Rami', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lope3266w6z@example.org', '+5731556677888888', 15000000.00, '12333', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('1ee0dc54-c720-4306-8400-596bc03ff57d'::uuid, '12333', 'Carlos', 'RamiR', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lope3266w65z@example.org', '+5731556677888888', 15000000.00, '12333', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('d37d8486-b5dd-459a-b63f-af52c7340e6e'::uuid, '12333', 'Carlos', 'RamiR', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lopes3266w65z@example.org', '+5731556677888888', 15000000.00, '12333', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('24ef8b74-dd32-449f-a3ea-8e725a60c957'::uuid, '12333', 'Carlos', 'Rami', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lopez2@example.org', '+5731556677888888', 15000000.00, '12333', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('efc4f265-5c11-490f-853e-d046422336f1'::uuid, '12333', 'Carlos', 'Rami', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lopez27@example.org', '+5731556677888888', 15000000.00, '12345', 1);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('b975fb1a-b6a2-4204-9422-850c17addd3b'::uuid, '12333', 'Carlos', 'Rami', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lopez237@example.org', '+5731556677888888', 15000000.00, '12345', 3);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('ffce4a6a-7b9d-4dc8-bc32-754a3cc614f6'::uuid, '12333', 'Andres', 'Lopez', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lopezSoto@example.org', '+5731556677888888', 12000000.00, '12345', 2);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('06421574-0e31-4e00-8bf0-78d63cbee828'::uuid, '12333', 'Andres', 'Lopez', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lopezSoto1@example.org', '+5731556677888888', 12000000.00, '12345', 2);
INSERT INTO public.users
(user_id, document_id, "name", lastname, birth_date, address, email, phone, base_salary, password_hash, id_rol)
VALUES('ab952ecf-31d4-488d-bc6d-000b17d6771c'::uuid, '12333', 'Andres', 'Lopez', '2024-12-15', 'Carrera 45 #67-89, Bogotá', 'lopezSoto12@example.org', '+5731556677888888', 12000000.00, '12345', 2);