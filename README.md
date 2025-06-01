# lab-spring-boot
Leaning best practices with java spring boot

# How to run k8s
kubectl apply -f k8s-deployment/secret.yaml
kubectl apply -f k8s-deployment/configmap.yaml
kubectl apply -f k8s-deployment/postgres.yaml
kubectl apply -f k8s-deployment/redis.yaml
kubectl apply -f k8s-deployment/app.yaml
kubectl get pods

# How to connect db 
psql -U myapp_user -d myapp_db
# How to check table
\dt

# How to down k8s
## Scale deployment เป็น 0 (หยุดชั่วคราวโดยไม่ลบ)

kubectl scale deployment pwm-app --replicas=0
kubectl scale deployment pwm-postgres --replicas=0
kubectl scale deployment pwm-redis --replicas=0

## Restart
kubectl scale deployment pwm-app --replicas=2
kubectl scale deployment pwm-postgres --replicas=1
kubectl scale deployment pwm-redis --replicas=1