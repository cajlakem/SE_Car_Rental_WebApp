docker stop spring_container
docker stop rental_db
docker rm spring_container
docker rm rental_db
docker rmi docker_backend_spring:latest
docker-compose up -d
