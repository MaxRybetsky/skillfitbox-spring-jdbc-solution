-- Create trainers table
CREATE TABLE trainers (
    id UUID PRIMARY KEY,
    surname VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100),
    phone VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('WORKING', 'ON_LEAVE', 'NOT_WORKING')),
    created_datetime TIMESTAMP NOT NULL,
    updated_datetime TIMESTAMP NOT NULL
);

-- Create lockers table
CREATE TABLE lockers (
    id UUID PRIMARY KEY,
    number INTEGER NOT NULL UNIQUE,
    client_id UUID,
    created_datetime TIMESTAMP NOT NULL,
    updated_datetime TIMESTAMP NOT NULL
);

-- Create services table
CREATE TABLE services (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    created_datetime TIMESTAMP NOT NULL,
    updated_datetime TIMESTAMP NOT NULL
);

-- Create clients table
CREATE TABLE clients (
    id UUID PRIMARY KEY,
    surname VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100),
    birthday DATE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    locker_id UUID,
    trainer_id UUID,
    created_datetime TIMESTAMP NOT NULL,
    updated_datetime TIMESTAMP NOT NULL,
    FOREIGN KEY (locker_id) REFERENCES lockers(id),
    FOREIGN KEY (trainer_id) REFERENCES trainers(id)
);

-- Create client_services junction table for many-to-many relationship
CREATE TABLE client_services (
    client_id UUID NOT NULL,
    service_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (client_id, service_id),
    FOREIGN KEY (service_id) REFERENCES services(id)
);

-- Add foreign key constraint for client_services after clients table is created
ALTER TABLE client_services 
ADD CONSTRAINT fk_client_services_client 
FOREIGN KEY (client_id) REFERENCES clients(id);
