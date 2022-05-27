The project is running all the time on 66.135.2.93/8081. Just in case something goes wrong in deployment.
1. To run the project, mysql8.0, jre11, and nodejs 16.13.1 need to be installed.
2. The back-end system is set to link to a cloud database be default. There is no need to create a local database. However, you can always create a local database using file "/sql/create_tables.sql"
3. When using a local database, the password and username need to be set to “root” and “12345678”, then run java -jar local.jar to start the backend system. When using the cloud database, run java -jar cloud.jar to start the backend system. The back-end system will use port 8080
4. For the front-end system, go to the directory "/front-end" first, run "npm install vue-cli", and then run "npm start serve"
5. The project will be available at https://localhost:8081/
6. There is another anaylze program performing anaylze tasks every 100 seconds. Run "java -jar analyze-local.jar" to start it.




Reference
https://vuejs.org/
https://spring.io/projects/spring-boot
https://stackoverflow.com/

