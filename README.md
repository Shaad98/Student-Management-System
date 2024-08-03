# Student Management System

## Overview
The Student Management System is a simple Java Swing application that connects to a MySQL database. It allows users to log in, add new users, and display existing users from the database.

## Features
- Login with email and password
- Add new users with username, email, password, and ID
- Display all users with their usernames and IDs
- Email validation using regex during user registration

## Prerequisites
- Java Development Kit (JDK 21 or higher)
- MySQL Database
- MySQL Connector/J (JAR file)

## Database Setup
1. Install MySQL and start the MySQL server.
2. Create a database named `student`.
3. Create a table named `student` with the following columns:
   - `username` (VARCHAR)
   - `email` (VARCHAR)
   - `password` (VARCHAR)
   - `id` (VARCHAR)

```sql
CREATE DATABASE student;
USE student;

CREATE TABLE student (
    username VARCHAR(50),
    email VARCHAR(50),
    password VARCHAR(50),
    id VARCHAR(50)
);
