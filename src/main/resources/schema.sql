CREATE TABLE student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    score DOUBLE,
    graduate BOOLEAN,
    create_date TIMESTAMP
);

CREATE TABLE bpi (
--	id INT AUTO_INCREMENT,
    code VARCHAR(30),
    symbol VARCHAR(30),
    rate VARCHAR(30),
    description VARCHAR(30),
    rate_float VARCHAR(30)
);