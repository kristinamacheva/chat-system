# Chat Platform - Final Course Project

## Overview

This project is a real-time chat platform developed as part of a final course project. The platform enables users to create channels, send messages, manage friends and define user roles within channels.

## Features

- **User Management:**
  - Searching for other users
  - Adding users to a friend list
- **Channel Management:**
  - Creating and deleting channels
  - Adding users to channels
  - Assigning roles (Owner, Admin, Guest)
- **Messaging System:**
  - Sending direct messages to friends
  - Sending messages in channels
  - Viewing chat history
- **Role-Based Access Control:**
  - Owners can manage channels (add/remove users, rename and delete channels, assign roles)
  - Admins can add users and rename channels
  - Guests can only send and read messages
- **Soft Delete:**
  - Data deletion is implemented using soft delete to preserve history
- **API Testing:**
  - Postman collection included for API validation

## Technology Stack

### Frontend:

- React
- Chakra UI (for styling)

### Backend:

- Spring Boot (REST API)
- Spring Data JPA (for database interactions)
- H2 Database (for development and testing)
- Flyway (for database migrations)
- Lombok (to reduce boilerplate code)
- BCrypt (for password hashing)
