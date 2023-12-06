CREATE TABLE customers(
    customerId BIGSERIAL PRIMARY KEY,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE ,
    age INT NOT NULL
);
