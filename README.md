# Practical Task: Library Book Tracker API

Starter project for Data Architecture practical task: Build a REST API for managing books in a library using Spring Boot, Spring Data JPA and an H2 database.

## Task
Complete all of the 'TODO' portions of the code. The finished application should compile and all of the controller endpoints should be functional.

## Behavioural Requirements
- Books can be created
- All books can be retrieved
- A single book can be retrieved by ID
- Books can be deleted
- Books can be filtered and searched by their fields.
- Controller delegates to Service, which delegates to Repository
- DTOs are used for requests and responses

## Stretch goals
- Request validation
- Custom exception handling
- Searching by partial title
- Add 'borrow' and 'return' endpoints along with the new 'borrowedStatus' field to the Book entity

## M11 Task
Practice Starting point: A Spring Boot application with JPA, a browser UI, and REST endpoints. 
#
Tasks: 
  1. Configure Spring Security with SecurityFilterChain
  2. Add browser form login and protect the UI
  3. Keep CSRF enabled for browser forms
  4. Protect REST endpoints: GET → authenticated users POST, PUT, DELETE → ADMIN only
  5. Configure a database-backed user with: • BCrypt password • USER or ADMIN role
  6. Add one secured outbound API call: • Send an API key or bearer token • Store the credential outside source code
  7. Test:
     - • Unauthenticated request → 401 or login page
     - • USER reading data → success
     - • USER attempting a write → 403
     - • ADMIN writing data → success
     - • Missing CSRF token → rejected
     - • External API failure → handled clearly
