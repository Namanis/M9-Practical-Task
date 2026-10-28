# M11 Task 7: Testing

## Unauthenticated request → 401 or login page

<img width="1200" height="290" alt="Screenshot 2026-10-28 230916" src="https://github.com/user-attachments/assets/35d84294-3fff-4604-a290-2a62e51f347e" />

## USER reading data → success

<img width="1192" height="737" alt="Screenshot 2026-10-28 230940" src="https://github.com/user-attachments/assets/5df079e6-e9f2-4772-8b62-f0f5033a2d73" />

## USER attempting a write → 403

<img width="1197" height="455" alt="Screenshot 2026-10-28 230959" src="https://github.com/user-attachments/assets/55833a55-d0e4-4403-80da-4dbb7820c21b" />

## ADMIN writing data → success

<img width="1210" height="504" alt="Screenshot 2026-10-28 231025" src="https://github.com/user-attachments/assets/e7dbb189-17be-4d07-bd31-ca3a606c3fbc" />

## Missing CSRF token → rejected

<img width="941" height="302" alt="Screenshot 2026-10-28 231054" src="https://github.com/user-attachments/assets/4effa2b7-800a-4234-9987-5ae638e4c8b8" />

## External API failure → handled clearly

<img width="1147" height="462" alt="Screenshot 2026-10-28 231320" src="https://github.com/user-attachments/assets/2d83b82a-e075-4f15-8bbd-57a7a76e93e1" />

<img width="916" height="497" alt="Screenshot 2026-10-28 231107" src="https://github.com/user-attachments/assets/e978415e-7d03-414d-b7ed-b67574e27719" />







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
