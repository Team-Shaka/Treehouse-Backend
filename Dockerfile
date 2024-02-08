# 기본 이미지 설정
FROM eclipse-temurin:17-jdk

# 현재 리포지토리, api gateway의 코드를 바탕으로 JAR 생성
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 최종 이미지 생성
FROM eclipse-temurin:17-jdk
COPY --from=0 app.jar app.jar

# 애플리케이션 실행 스크립트 추가
COPY dev_start.sh start.sh
RUN chmod +x start.sh

EXPOSE 8080

ENTRYPOINT ["./start.sh"]
