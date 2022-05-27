1. To run the project, mysql8.0, jre11, and nodejs 16.13.1 need to be installed.
2. The back-end system is set to link to a cloud database be default. Create a local database using file "/sql/create_tables.sql"
3. The password and username need to be set to “root” and “12345678”, then run java -jar local.jar to start the backend system. The back-end system will use port 8080
4. For the front-end system, go to the directory "/front-end" first, run "npm install vue-cli", and then run "npm start serve"
5. The project will be available at https://localhost:8081/
6. There is another anaylze program performing anaylze tasks every 100 seconds. Run "java -jar analyze-local.jar" to start it.

Reference
https://vuejs.org/
https://spring.io/projects/spring-boot
https://stackoverflow.com/

