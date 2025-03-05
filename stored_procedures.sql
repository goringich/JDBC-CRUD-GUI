-- stored_procedures.sql

-- Create a new database
CREATE OR REPLACE PROCEDURE create_database()
LANGUAGE plpgsql
AS $$
BEGIN
  EXECUTE 'CREATE DATABASE sql-laba5';
END $$;

CREATE TABLE IF NOT EXISTS frameworks (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);


-- Create the "frameworks" table if it does not exist
CREATE OR REPLACE PROCEDURE create_table()
LANGUAGE plpgsql
AS $$
BEGIN
  CREATE TABLE IF NOT EXISTS frameworks (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW()
  );
END $$;

-- Truncate the "frameworks" table (clear all data)
CREATE OR REPLACE PROCEDURE clear_table()
LANGUAGE plpgsql
AS $$
BEGIN
  TRUNCATE TABLE frameworks;
END $$;

-- Add a new record into the "frameworks" table
CREATE OR REPLACE PROCEDURE add_framework(p_name TEXT, p_type TEXT, p_description TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
  INSERT INTO frameworks (name, type, description)
  VALUES (p_name, p_type, p_description);
END $$;

-- Search for records in the "frameworks" table by name (case-insensitive)
CREATE OR REPLACE FUNCTION search_framework(p_name TEXT)
RETURNS TABLE(id INT, name TEXT, type TEXT, description TEXT, created_at TIMESTAMP)
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN QUERY SELECT * FROM frameworks WHERE name ILIKE '%' || p_name || '%';
END $$;

-- Update a record in the "frameworks" table
CREATE OR REPLACE FUNCTION update_framework(p_old_name TEXT, p_new_name TEXT, p_new_type TEXT, p_new_description TEXT)
RETURNS INT
LANGUAGE plpgsql
AS $$
DECLARE
  row_count INT;
BEGIN
  UPDATE frameworks
  SET name = p_new_name, type = p_new_type, description = p_new_description
  WHERE name = p_old_name;
  GET DIAGNOSTICS row_count = ROW_COUNT;
  RETURN row_count;
END $$;


-- Delete a record from the "frameworks" table by name
CREATE OR REPLACE PROCEDURE delete_framework(p_name TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
  DELETE FROM frameworks WHERE name = p_name;
END $$;

-- Get all records from the "frameworks" table
CREATE OR REPLACE FUNCTION get_all_frameworks()
RETURNS TABLE(id INT, name TEXT, type TEXT, description TEXT, created_at TIMESTAMP)
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN QUERY SELECT * FROM frameworks;
END $$;

-- Create a new database user with a specified role
CREATE OR REPLACE PROCEDURE create_user(p_username TEXT, p_password TEXT, p_role TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
  -- Use %I for identifier and %L for literal
  EXECUTE format('CREATE ROLE %I WITH LOGIN PASSWORD %L', p_username, p_password);
  IF p_role = 'guest' THEN
    EXECUTE format('GRANT SELECT ON frameworks TO %I', p_username);
  ELSIF p_role = 'admin' THEN
    EXECUTE format('GRANT ALL PRIVILEGES ON frameworks TO %I', p_username);
  END IF;
END $$;
