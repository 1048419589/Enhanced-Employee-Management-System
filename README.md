# Employee Management System – Enhanced Version

**Statement:** This project is a **refactored version** based on the open-source GitHub project  
[Employee_Management_System](https://github.com/NipuniVithana/Employee_Management_System) (original author: NipuniVithana).  
All modifications are limited to code standardization, logic fixes, and performance optimizations on top of the original implementation.

## Tech Stack

* **Backend**: Java, Spring Boot, Spring Security, JPA
* **Frontend**: Thymeleaf, HTML, CSS, JavaScript (Bootstrap)
* **Database**: MySQL
* **Cache**: Redis
* **Build Tool**: Maven

## Refactoring Details

* Renamed all non-standard file and package names to follow standard conventions, and corrected original spelling errors.
* Optimized page navigation logic and fixed the 404 error caused by clicking the sidebar under second-level directories.
* Fixed the issue where unauthenticated access to pages did not automatically redirect to the login page.
* Removed unused decorative files and build-generated junk files, simplifying the project directory structure.
* Fixed the issue where unchanged fields were unintentionally cleared (set to `null`) when editing employee information.
* Added data validation for input, preventing insertion of employee records with all fields empty.
* Introduced Redis for caching; homepage statistics are now retrieved from Redis with priority.
* Refactored Controller-layer logic and merged duplicated endpoints.
