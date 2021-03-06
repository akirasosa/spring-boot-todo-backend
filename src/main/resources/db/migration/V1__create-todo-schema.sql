CREATE TABLE _todo (
    id bigint NOT NULL,
    title character varying(255) NOT NULL
);
CREATE SEQUENCE todo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE todo_id_seq OWNED BY _todo.id;
ALTER TABLE ONLY _todo ALTER COLUMN id SET DEFAULT nextval('todo_id_seq'::regclass);
ALTER TABLE ONLY _todo
    ADD CONSTRAINT todo_pkey PRIMARY KEY (id);
