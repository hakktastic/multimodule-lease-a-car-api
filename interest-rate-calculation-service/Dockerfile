# improved caching
FROM openjdk:8-jdk-alpine
EXPOSE 8083
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","nl.svb.leaseacarapi.leasecalculationservice.InterestRateCalculationServiceApplication"]