-- Inserting two records with Role as 'ADMIN' and set password as 123

INSERT INTO users (first_name, last_name, email, password, role)
SELECT 'Tausif', 'Ahmad', 'tausif@gmail.com', '$2a$10$OLTxqQNqfwwit7E6nAf.Quh3eLbzAl4N0It5TKtOFTSHw9pqlK5jO', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'tausif@gmail.com');

INSERT INTO users (first_name, last_name, email, password, role)
SELECT 'Admin', 'Admin', 'admin@gmail.com', '$2a$10$3rmt2.scM1z4YHVHdh90JOwNjZMZOmfmsoWd/1RNrEDIQuC2VBVxi', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@gmail.com');

