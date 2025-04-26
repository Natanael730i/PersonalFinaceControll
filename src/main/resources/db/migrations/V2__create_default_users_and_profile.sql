CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Cria perfis se não existirem
INSERT INTO profile (id, code, name, role)
SELECT gen_random_uuid(), 1, 'ADMIN', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM profile WHERE name = 'ADMIN'
);

INSERT INTO profile (id, code, name, role)
SELECT gen_random_uuid(), 2, 'USER', 'USER'
WHERE NOT EXISTS (
    SELECT 1 FROM profile WHERE name = 'USER'
);

-- Cria usuário admin se não existir
INSERT INTO users (id, name, email, password, profiles_id)
SELECT gen_random_uuid(), 'admin', 'admin', crypt('admin', gen_salt('bf')),
       (SELECT id FROM profile WHERE name = 'ADMIN')
WHERE NOT EXISTS (
    SELECT 1 FROM USERS WHERE email = 'admin'
);

-- Cria usuário comum se não existir
INSERT INTO USERS (id, name, email, password, profiles_id)
SELECT gen_random_uuid(), 'user', 'user', crypt('user', gen_salt('bf')),
       (SELECT id FROM profile WHERE name = 'USER')
WHERE NOT EXISTS (
    SELECT 1 FROM USERS WHERE email = 'user'
);
