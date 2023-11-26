CREATE TABLE operations
(
    id VARCHAR(30),
    montant DOUBLE NOT NULL,
    date TIMESTAMP NOT NULL,
    identifiant_compte VARCHAR(30),
    PRIMARY KEY (id)
);