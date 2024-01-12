--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0 (Debian 16.0-1.pgdg120+1)
-- Dumped by pg_dump version 16.1

-- Started on 2023-12-24 22:38:31

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16463)
-- Name: eshop; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA eshop;


--
-- TOC entry 224 (class 1259 OID 16484)
-- Name: client_seq; Type: SEQUENCE; Schema: eshop; Owner: -
--

CREATE SEQUENCE eshop.client_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 225 (class 1259 OID 16485)
-- Name: client; Type: TABLE; Schema: eshop; Owner: -
--

CREATE TABLE eshop.client (
    id bigint DEFAULT nextval('eshop.client_seq'::regclass) NOT NULL,
    document_number character varying(13),
    email character varying(128),
    name character varying(128)
);


--
-- TOC entry 220 (class 1259 OID 16474)
-- Name: order_product_seq; Type: SEQUENCE; Schema: eshop; Owner: -
--

CREATE SEQUENCE eshop.order_product_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 221 (class 1259 OID 16475)
-- Name: order_product; Type: TABLE; Schema: eshop; Owner: -
--

CREATE TABLE eshop.order_product (
    id bigint DEFAULT nextval('eshop.order_product_seq'::regclass) NOT NULL,
    quantity integer,
    unit_cost numeric(10,2),
    order_id bigint,
    shop_product_id bigint
);


--
-- TOC entry 222 (class 1259 OID 16479)
-- Name: orders_seq; Type: SEQUENCE; Schema: eshop; Owner: -
--

CREATE SEQUENCE eshop.orders_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 223 (class 1259 OID 16480)
-- Name: orders; Type: TABLE; Schema: eshop; Owner: -
--

CREATE TABLE eshop.orders (
    id bigint DEFAULT nextval('eshop.orders_seq'::regclass) NOT NULL,
    order_creation_date timestamp without time zone,
    status character varying(128),
    total_cost numeric(10,2),
    client_id bigint,
    provider_id bigint
);


--
-- TOC entry 228 (class 1259 OID 16494)
-- Name: provider_seq; Type: SEQUENCE; Schema: eshop; Owner: -
--

CREATE SEQUENCE eshop.provider_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 229 (class 1259 OID 16495)
-- Name: provider; Type: TABLE; Schema: eshop; Owner: -
--

CREATE TABLE eshop.provider (
    id bigint DEFAULT nextval('eshop.provider_seq'::regclass) NOT NULL,
    document_number character varying(13),
    name character varying(128)
);


--
-- TOC entry 218 (class 1259 OID 16469)
-- Name: shop_seq; Type: SEQUENCE; Schema: eshop; Owner: -
--

CREATE SEQUENCE eshop.shop_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 219 (class 1259 OID 16470)
-- Name: shop; Type: TABLE; Schema: eshop; Owner: -
--

CREATE TABLE eshop.shop (
    id bigint DEFAULT nextval('eshop.shop_seq'::regclass) NOT NULL,
    address character varying(128),
    name character varying(128)
);


--
-- TOC entry 216 (class 1259 OID 16464)
-- Name: shop_product_seq; Type: SEQUENCE; Schema: eshop; Owner: -
--

CREATE SEQUENCE eshop.shop_product_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 217 (class 1259 OID 16465)
-- Name: shop_product; Type: TABLE; Schema: eshop; Owner: -
--

CREATE TABLE eshop.shop_product (
    id bigint DEFAULT nextval('eshop.shop_product_seq'::regclass) NOT NULL,
    cost numeric(10,2),
    name character varying(128),
    stock integer,
    shop_id bigint
);


--
-- TOC entry 226 (class 1259 OID 16489)
-- Name: users_seq; Type: SEQUENCE; Schema: eshop; Owner: -
--

CREATE SEQUENCE eshop.users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 227 (class 1259 OID 16490)
-- Name: users; Type: TABLE; Schema: eshop; Owner: -
--

CREATE TABLE eshop.users (
    id bigint DEFAULT nextval('eshop.users_seq'::regclass) NOT NULL,
    name character varying(64),
    password character varying(128),
    person_id bigint,
    role character varying(64)
);


--
-- TOC entry 3412 (class 0 OID 16485)
-- Dependencies: 225
-- Data for Name: client; Type: TABLE DATA; Schema: eshop; Owner: -
--

INSERT INTO eshop.client VALUES (1, '1720997905', 'genarosulca@hotmail.com', 'Genaro Sulca');
INSERT INTO eshop.client VALUES (2, '9912123456', 'taniasulca@hotmail.com', 'Tania Sulca');


--
-- TOC entry 3408 (class 0 OID 16475)
-- Dependencies: 221
-- Data for Name: order_product; Type: TABLE DATA; Schema: eshop; Owner: -
--

INSERT INTO eshop.order_product VALUES (1, 1, 4.00, 1, 7);
INSERT INTO eshop.order_product VALUES (2, 1, 3.50, 1, 8);
INSERT INTO eshop.order_product VALUES (3, 1, 3.50, 1, 4);
INSERT INTO eshop.order_product VALUES (4, 1, 3.50, 2, 8);
INSERT INTO eshop.order_product VALUES (5, 1, 6.00, 2, 9);
INSERT INTO eshop.order_product VALUES (6, 1, 1.70, 2, 3);
INSERT INTO eshop.order_product VALUES (7, 1, 2.00, 3, 1);
INSERT INTO eshop.order_product VALUES (8, 1, 1.80, 3, 5);


--
-- TOC entry 3410 (class 0 OID 16480)
-- Dependencies: 223
-- Data for Name: orders; Type: TABLE DATA; Schema: eshop; Owner: -
--

INSERT INTO eshop.orders VALUES (1, '2023-12-24 22:08:37.507', '1', 12.32, 1, 1);
INSERT INTO eshop.orders VALUES (2, '2023-12-24 22:10:51.553', '1', 12.54, 1, 2);
INSERT INTO eshop.orders VALUES (3, '2023-12-24 22:27:31.006', '1', 4.26, 2, 1);


--
-- TOC entry 3416 (class 0 OID 16495)
-- Dependencies: 229
-- Data for Name: provider; Type: TABLE DATA; Schema: eshop; Owner: -
--

INSERT INTO eshop.provider VALUES (1, '1234567890001', 'Supermaxi');
INSERT INTO eshop.provider VALUES (2, '0987654321001', 'Tia');


--
-- TOC entry 3406 (class 0 OID 16470)
-- Dependencies: 219
-- Data for Name: shop; Type: TABLE DATA; Schema: eshop; Owner: -
--

INSERT INTO eshop.shop VALUES (1, 'Condado shopping', 'Juris');
INSERT INTO eshop.shop VALUES (2, 'Condado shopping', 'Moderna');
INSERT INTO eshop.shop VALUES (3, 'Condado shopping', 'Cerveceria nacional');


--
-- TOC entry 3404 (class 0 OID 16465)
-- Dependencies: 217
-- Data for Name: shop_product; Type: TABLE DATA; Schema: eshop; Owner: -
--

INSERT INTO eshop.shop_product VALUES (2, 2.50, 'Club premium', 300, 3);
INSERT INTO eshop.shop_product VALUES (6, 5.20, 'Pan de masa madre', 500, 2);
INSERT INTO eshop.shop_product VALUES (7, 4.00, 'Salchicha polaca', 499, 1);
INSERT INTO eshop.shop_product VALUES (4, 3.50, 'Pan de molde', 499, 2);
INSERT INTO eshop.shop_product VALUES (8, 3.50, 'Jamón de espalda', 298, 1);
INSERT INTO eshop.shop_product VALUES (9, 6.00, 'Mortadela grande', 499, 1);
INSERT INTO eshop.shop_product VALUES (3, 1.70, 'Budweiser', 499, 3);
INSERT INTO eshop.shop_product VALUES (1, 2.00, 'Pilsener', 499, 3);
INSERT INTO eshop.shop_product VALUES (5, 1.80, 'Rosquillas', 299, 2);


--
-- TOC entry 3414 (class 0 OID 16490)
-- Dependencies: 227
-- Data for Name: users; Type: TABLE DATA; Schema: eshop; Owner: -
--

INSERT INTO eshop.users VALUES (2, 'admin', '$2a$10$46Yd87JcFWR0u2ZSWT10..MOGIlw/k9EFTpdyFJg2UTucVeIGsHaq', NULL, 'ADMIN');
INSERT INTO eshop.users VALUES (3, 'gsulca', '$2a$10$46Yd87JcFWR0u2ZSWT10..MOGIlw/k9EFTpdyFJg2UTucVeIGsHaq', NULL, 'USER');


--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 224
-- Name: client_seq; Type: SEQUENCE SET; Schema: eshop; Owner: -
--

SELECT pg_catalog.setval('eshop.client_seq', 2, true);


--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 220
-- Name: order_product_seq; Type: SEQUENCE SET; Schema: eshop; Owner: -
--

SELECT pg_catalog.setval('eshop.order_product_seq', 8, true);


--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 222
-- Name: orders_seq; Type: SEQUENCE SET; Schema: eshop; Owner: -
--

SELECT pg_catalog.setval('eshop.orders_seq', 3, true);


--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 228
-- Name: provider_seq; Type: SEQUENCE SET; Schema: eshop; Owner: -
--

SELECT pg_catalog.setval('eshop.provider_seq', 2, true);


--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 216
-- Name: shop_product_seq; Type: SEQUENCE SET; Schema: eshop; Owner: -
--

SELECT pg_catalog.setval('eshop.shop_product_seq', 9, true);


--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 218
-- Name: shop_seq; Type: SEQUENCE SET; Schema: eshop; Owner: -
--

SELECT pg_catalog.setval('eshop.shop_seq', 3, true);


--
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 226
-- Name: users_seq; Type: SEQUENCE SET; Schema: eshop; Owner: -
--

SELECT pg_catalog.setval('eshop.users_seq', 3, true);


--
-- TOC entry 3250 (class 2606 OID 16512)
-- Name: client pk_client; Type: CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.client
    ADD CONSTRAINT pk_client PRIMARY KEY (id);


--
-- TOC entry 3246 (class 2606 OID 16508)
-- Name: order_product pk_order_product; Type: CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.order_product
    ADD CONSTRAINT pk_order_product PRIMARY KEY (id);


--
-- TOC entry 3248 (class 2606 OID 16510)
-- Name: orders pk_orders; Type: CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.orders
    ADD CONSTRAINT pk_orders PRIMARY KEY (id);


--
-- TOC entry 3254 (class 2606 OID 16500)
-- Name: provider pk_provider; Type: CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.provider
    ADD CONSTRAINT pk_provider PRIMARY KEY (id);


--
-- TOC entry 3244 (class 2606 OID 16506)
-- Name: shop pk_shop; Type: CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.shop
    ADD CONSTRAINT pk_shop PRIMARY KEY (id);


--
-- TOC entry 3242 (class 2606 OID 16504)
-- Name: shop_product pk_shop_product; Type: CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.shop_product
    ADD CONSTRAINT pk_shop_product PRIMARY KEY (id);


--
-- TOC entry 3252 (class 2606 OID 16502)
-- Name: users pk_users; Type: CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.users
    ADD CONSTRAINT pk_users PRIMARY KEY (id);


--
-- TOC entry 3256 (class 2606 OID 16518)
-- Name: order_product fk_order_product_pk_order; Type: FK CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.order_product
    ADD CONSTRAINT fk_order_product_pk_order FOREIGN KEY (order_id) REFERENCES eshop.orders(id);


--
-- TOC entry 3257 (class 2606 OID 16523)
-- Name: order_product fk_order_product_pk_shop_product; Type: FK CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.order_product
    ADD CONSTRAINT fk_order_product_pk_shop_product FOREIGN KEY (shop_product_id) REFERENCES eshop.shop_product(id);


--
-- TOC entry 3258 (class 2606 OID 16528)
-- Name: orders fk_orders_pk_client; Type: FK CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.orders
    ADD CONSTRAINT fk_orders_pk_client FOREIGN KEY (client_id) REFERENCES eshop.client(id);


--
-- TOC entry 3255 (class 2606 OID 16513)
-- Name: shop_product fk_shop_product_pk_shop; Type: FK CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.shop_product
    ADD CONSTRAINT fk_shop_product_pk_shop FOREIGN KEY (shop_id) REFERENCES eshop.shop(id);


--
-- TOC entry 3259 (class 2606 OID 16533)
-- Name: users fk_users_pk_client; Type: FK CONSTRAINT; Schema: eshop; Owner: -
--

ALTER TABLE ONLY eshop.users
    ADD CONSTRAINT fk_users_pk_client FOREIGN KEY (person_id) REFERENCES eshop.client(id);


-- Completed on 2023-12-24 22:38:32

--
-- PostgreSQL database dump complete
--

