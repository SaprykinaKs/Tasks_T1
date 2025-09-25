### Task
Three *banking system* microservices: client-processing, account-processing, credit-processing

### 1.Run
```bash
docker compose build
docker compose up -d
```

### 2. Check
```bash
docker compose ps
```

- **Client Processing Service**: http://localhost:8081
- **Account Processing Service**: http://localhost:8082  
- **Credit Processing Service**: http://localhost:8083

```bash
curl http://localhost:8081/api/clients/health
curl http://localhost:8082/api/accounts/health
curl http://localhost:8083/api/credits/health
```

### 3. Stop
```bash
docker compose down
```