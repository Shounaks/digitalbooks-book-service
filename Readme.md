## DigitalBooks Microservice - Book MS

Following is the list of enviornment Variables Needed:
- MYSQL_USER = ${MYSQL_USER} , `Eg. root`
- MYSQL_PASSWORD = ${MYSQL_USER_PASSWORD} `Eg. root,qwerty`
- MYSQL_HOST = ${MYSQL_HOST} `Eg. localhost`
- MYSQL_PORT = ${MYSQL_PORT} `Eg. 3306`
- MYSQL_DATABASE = ${MYSQL_DATABASE_NAME} `Eg. book_db`
- MYSQL_URL = "mysql://${{ MYSQL_USER }}:${{ MYSQL_PASSWORD }}@${{ MYSQL_HOST }}:${{ MYSQL_PORT }}/${{ MYSQL_DATABASE }}