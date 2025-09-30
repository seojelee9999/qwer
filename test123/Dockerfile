# =======================================================
# 1단계: 빌드 스테이지 (Build Stage)
# 목적: 무거운 JDK 환경에서 소스 코드를 컴파일하고 JAR 파일을 생성합니다.
# =======================================================
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# 1. 빌드 도구 파일 복사 (Gradle/Maven)
# 프로젝트 루트에 있는 빌드 관련 파일(gradlew, build.gradle 등)을 복사합니다.
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY gradle gradle
# 만약 build.gradle이 참조하는 다른 파일(예: libs)이 있다면 함께 복사합니다.

# 2. 소스 코드 복사
# src 디렉터리 전체를 복사합니다.
COPY src /app/src

# 3. JAR 파일 빌드 실행
# -x test 옵션으로 테스트를 건너뛰고 실행 가능한 JAR 파일을 생성합니다.
RUN ./gradlew clean bootJar -x test


FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=build /app/build/libs/test123-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
#도커가 호스트로부터 가로챌 포트번호가아니라 도커내부에서 사용하는 포트번호

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]