FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/notificacoes-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-db.sh /app/wait-for-db.sh
RUN chmod +x /app/wait-for-db.sh
ENTRYPOINT ["/app/wait-for-db.sh"]