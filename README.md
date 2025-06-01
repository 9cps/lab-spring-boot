# 🔐 PWM Service

Spring Boot RESTful API สำหรับจัดการบัญชีผู้ใช้ เชื่อมต่อกับ PostgreSQL และ Redis พร้อมรองรับการใช้งานผ่าน Docker และ Kubernetes

## 📦 Tech Stack
- Java 21 + Spring Boot 3.x
- PostgreSQL 15
- Redis 7
- Docker / Docker Compose
- Kubernetes
- k6 (Load Testing)
- Swagger UI

## 🏛️ Architecture

ออกแบบโดยใช้หลักการ Layer architecture เพื่อให้จัดการ Code ได้ง่ายโดยโครงสร้างจะเป็นดังนี้
```sh
src/
 └── main/
      ├── java/com/example/project/
      │    ├── controller/        <-- REST controllers
      │    ├── business/          <-- Business logic
      │    ├── service/           <-- Service
      │    ├── repository/        <-- JPA repositories
      │    ├── model/             <-- Entities (JPA)
      │    ├── dto/               <-- Request/Response DTOs
      │    ├── config/            <-- Spring configs (DB, CORS, Swagger, etc.)
      │    ├── exception/         <-- Custom exceptions & handlers
      │    └── ProjectApplication.java
      └── resources/
           ├── application.yml    <-- Configuration
           └── ...
```
## 🚀 Getting Started
---

### 🐳 Run Docker Compose

```bash
docker-compose up --build
```
จากนั้นเปิด Swagger ได้ที่: http://localhost:8080/swagger-ui/index.html

### 🛢️ Script sql mockup
```bash
-- USERS
INSERT INTO users (id, name, email, created_at) VALUES(1, 'Alice Chan', 'alice@example.com', CURRENT_TIMESTAMP),(2, 'Bob Lee', 'bob@example.com', CURRENT_TIMESTAMP),(3, 'Charlie Tan', 'charlie@example.com', CURRENT_TIMESTAMP);

-- ACCOUNTS
INSERT INTO accounts (id, user_id, currency, created_at) VALUES(1, 1, 'THB', CURRENT_TIMESTAMP),(2, 2, 'THB', CURRENT_TIMESTAMP),(3, 1, 'USD', CURRENT_TIMESTAMP),(4, 3, 'THB', CURRENT_TIMESTAMP);

-- ACCOUNT_BALANCES
INSERT INTO account_balances (account_id, balance, updated_at) VALUES(1, 5000.00, CURRENT_TIMESTAMP),(2, 3000.00, CURRENT_TIMESTAMP),(3, 1000.00, CURRENT_TIMESTAMP),(4, 800.00, CURRENT_TIMESTAMP);

-- TRANSACTIONS
INSERT INTO transactions (from_account_id, to_account_id, amount, status, description, created_at) VALUES(1, 2, 500.00, 'SUCCESS', 'Payment to Bob', CURRENT_TIMESTAMP),(2, 1, 200.00, 'SUCCESS', 'Refund to Alice', CURRENT_TIMESTAMP),(3, 4, 100.00, 'PENDING', 'USD to THB Transfer', CURRENT_TIMESTAMP);
```

### ☸️ ️Deployment with k8s

สำหรับขั้นตอนการสร้าง Pods, Loadbalance
```sh
kubectl apply -f k8s-deployment/secret.yaml
kubectl apply -f k8s-deployment/configmap.yaml
kubectl apply -f k8s-deployment/postgres.yaml
kubectl apply -f k8s-deployment/redis.yaml
kubectl apply -f k8s-deployment/app.yaml
```

ตรวจสอบ Pods ว่าทำงานถูกต้องแล้ว
```sh
kubectl get pods
```

ขั้นตอนการ Down pods | Scale deployment เป็น 0 (หยุดชั่วคราวโดยไม่ลบ)
```sh
kubectl scale deployment pwm-app --replicas=0
kubectl scale deployment pwm-postgres --replicas=0
kubectl scale deployment pwm-redis --replicas=0
```

ขั้นตอนการ Start pods
```sh
kubectl scale deployment pwm-app --replicas=2
kubectl scale deployment pwm-postgres --replicas=1
kubectl scale deployment pwm-redis --replicas=1
```

จากนั้นเปิด Swagger ได้ที่: http://localhost:30080/swagger-ui/index.htm

## 🧪 Performance test with k6

```sh
k6 run get-account-by-user-id.js
...
```