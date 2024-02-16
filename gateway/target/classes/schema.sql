CREATE TABLE ORDER_DTO (
    id SERIAL PRIMARY KEY,
    order_id BIGINT UNIQUE,
    date TIMESTAMP WITH TIME ZONE,
    rue VARCHAR(255),
    ville VARCHAR(255),
    code_postal VARCHAR(10),
    nom_livreur VARCHAR(255),
    telephone_livreur VARCHAR(20)
);
