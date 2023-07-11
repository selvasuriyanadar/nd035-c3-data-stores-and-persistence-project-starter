sudo docker run -p 3307:3306 --name critter-mysql -e MYSQL_ROOT_PASSWORD=root@123 -e MYSQL_DATABASE=critter -e MYSQL_USER=critter -e MYSQL_PASSWORD=critter@123 -d mysql:8.0
