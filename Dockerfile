# ---------- Builder Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# ننسخ الـ pom.xml وننزّل الـ dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# ننسخ الكود ونعمل البيلد
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Final Stage ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# ننسخ الـ jar من مرحلة الـ builder
COPY --from=builder /app/target/*-SNAPSHOT.jar app.jar

EXPOSE 8080

# نشغل التطبيق
ENTRYPOINT ["java", "-jar", "app.jar"]
