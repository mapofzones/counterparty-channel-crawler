# counterparty-channel-crawler

Running directly:
* java 11
* maven

Running in a container:
* Docker

## Usage

Running directly:
* `mvn package -DskipTests` or `mvn package`
* `java -jar /opt/app.jar --spring.profiles.active=prod`

Running in a container:
* `docker build -t counterparty-channel-crawler:v1 .`
* `docker run --env CR_SYNC_TIME="120s" --env DB_URL=jdbc:postgresql://<ip>:<port>/<db> --env DB_USER=<db_user> --env DB_PASS=<db_user_pass> -it -d --network="host" counterparty-channel-crawler:v1`