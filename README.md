# Employee

This is an employee **REST API**.

It has the following resources:
- Employee
- Bank Accounts: It's a sub resource of employee
- Company News
- Holidays

## How to run the project in IDE:
1. Open the project in IntelliJ
2. Open EmployeeApplication.kt
3. Run the file using play button beside main function
4. Go to the URL: http://localhost:8078/swagger-ui.html#  (This link will open the documentation of the API)

## Endpoints:
### To create/get/delete/update an employee:
- POST: '/employees' (to create an employee)
- GET: '/employees'  (it gets information of all employees)
- GET: '/employees/{employeeId}'  (it gets details of a particular employee with the given employeId)
- PUT: '/employees/{employeeId}'  ( to update the employee details)
- DELETE: '/employees/{employeeId}'  (to delete an employee)

### To create/get/delete/update an employee's bank accounts:
- POST: '/employees/{employeeId}/bank_accounts' (to create a bank account of an employee)
- GET: '/employees/{employeeId}/bank_accounts' (to get bank accounts of an employee)
- GET: '/employees/{employeeId}/bank_accounts/{id}'  (to get a particular bank account of an employee)
- PUT: '/employees/{employeeId}/bank_accounts/{id}'  (to update a bank account)
- DELETE: '/employees/{employeeId}/bank_accounts/{id}'  (to delete a bank account)

### To create/get/delete/update company news:
- POST: '/companyNews'  (to create news)
- GET : '/companyNews'  (to get details of news)
- GET: '/companyNews/{id}' (to get a particular news)
- PUT: '/companyNews/{id}' (to update news)
- DELETE: '/companyNews/{id}' (to delete news)

### To create/get/delete/update holidays:
- POST: '/holidays'  (to create a holiday)
- GET: '/holidays'  (to get details of all holidays)
- PUT: '/holidays/{id}'  (to update a holiday)
- DELETE: '/holidays/{id}'  (to delete a holiday)
