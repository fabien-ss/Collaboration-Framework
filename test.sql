INSERT INTO test(id, text, hoho_huhu, i) VALUES
    (default, 'haha', 'haha', 1),
    (default, 'hoho', 'hoho', 2),
    (default, 'huhu', 'huhu', 3),
    (default, 'hihi', 'hihi', 4);

CREATE table test2 (
    id VARCHAR(50) PRIMARY KEY,
    teny VARCHAR(50),
    cucu DOUBLE PRECISION
);
CREATE table test3 (
    id_3 SERIAL PRIMARY KEY,
    teny_3 VARCHAR(50)
);
INSERT INTO test3 VALUES
    (default, 'hohoho'),
    (default, 'huhuhu'),
    (default, 'hahaha');