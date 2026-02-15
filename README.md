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

## Visualizations

<img width="2556" height="1346" alt="Image" src="https://github.com/user-attachments/assets/7a502658-b1a3-4440-bc1f-e4778c6d2a1f" />
<img width="2559" height="1344" alt="Image" src="https://github.com/user-attachments/assets/c6e54fe0-201e-4241-b2fa-ddcf0b41647c" />
<img width="2552" height="1339" alt="Image" src="https://github.com/user-attachments/assets/ef147d4c-487c-40f2-9c66-c05723457088" />
<img width="2559" height="1345" alt="Image" src="https://github.com/user-attachments/assets/6f492929-bd9b-4765-8187-431bdc16f455" />
<img width="2559" height="1341" alt="Image" src="https://github.com/user-attachments/assets/ec7cc3ab-a7c4-4d4c-86fa-54acaa4c7967" />
<img width="2559" height="1345" alt="Image" src="https://github.com/user-attachments/assets/beef6647-c8c2-47ba-bf97-491e0bd6f999" />
<img width="653" height="444" alt="Image" src="https://github.com/user-attachments/assets/c5731bac-cb59-495a-8308-01a3c882ecb7" />
