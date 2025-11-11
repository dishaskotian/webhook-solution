# Webhook Hiring Assignment – Spring Boot Solution

This project is part of the **BHFL Java Developer Hiring Challenge**.  
The application automatically:

1. Generates a webhook by calling the `/generateWebhook/JAVA` API.
2. Receives `webhook` URL and `accessToken`.
3. Identifies the SQL question based on the registration number (odd → Question 1).
4. Reads the final SQL query from `final_query.sql`.
5. Submits the SQL query to the returned webhook endpoint with proper authorization.

---

## ✅ Developer Details

| Field | Value |
|------|-------|
| **Name** | Disha S Kotian |
| **Reg No** | PES2UG22CS1185 |
| **Email** | dishakotian06@gmail.com |

---

## 🛠 Technologies Used

- Java
- Spring Boot
- REST API (RestTemplate)
- JSON (Jackson)
- Maven

---

## 📂 Project Structure

webhook-solution/
├─ src/main/java/com/example/webhook/
│ ├─ WebhookApplication.java
│ └─ WebhookService.java
├─ src/main/resources/
│ ├─ application.properties
│ └─ final_query.sql
└─ pom.xml


---

## 🧠 SQL Query 

```sql
SELECT 
    P.AMOUNT AS SALARY,
    CONCAT(E.FIRST_NAME, ' ', E.LAST_NAME) AS NAME,
    TIMESTAMPDIFF(YEAR, E.DOB, CURDATE()) AS AGE,
    D.DEPARTMENT_NAME
FROM PAYMENTS P
JOIN EMPLOYEE E ON P.EMP_ID = E.EMP_ID
JOIN DEPARTMENT D ON E.DEPARTMENT = D.DEPARTMENT_ID
WHERE DAY(P.PAYMENT_TIME) <> 1
ORDER BY P.AMOUNT DESC
LIMIT 1;


How to run:
mvn clean package
java -jar target/webhook-solution-0.0.1-SNAPSHOT.jar


Submission Response:
{"success":true,"message":"Webhook processed successfully"}
