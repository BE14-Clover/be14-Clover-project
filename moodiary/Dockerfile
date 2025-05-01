FROM gradle:8.5-jdk17 AS build
WORKDIR /app/moodiary
COPY . .

## 테스트 코드 실행
#RUN gradle clean build --no-daemon
## 테스트 코드 스킵
RUN gradle clean build --no-daemon -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/moodiary/build/libs/*.jar ./
RUN mv $(ls *.jar | grep -v plain) app.jar
## 컨테이너 내부에서는 8080포트로 app.jar가 실행 됨
ENTRYPOINT ["java", "-jar", "app.jar"]