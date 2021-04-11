docker stop springcontainer
docker stop rental_db
docker rm springcontainer
docker rm rental_db
docker rmi docker_backend_spring:latest
docker-compose up -d
