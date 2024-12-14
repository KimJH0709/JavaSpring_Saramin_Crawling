# SaramIn Crawling Project

## 프로젝트 설명
이 프로젝트는 SaramIn 채용 정보를 크롤링하여 저장하고, 이를 REST API를 통해 제공하는 SpringBoot 기반 웹 애플리케이션입니다.

---

## 빌드 및 실행 방법

### 1. 필수 요구 사항
- Java 17
- Gradle 7.6 이상
- PostgreSQL 15.x
- Swagger UI를 통한 API 테스트 가능

### 2. 빌드 및 실행 명령어
아래 명령어를 실행하여 프로젝트를 빌드하고 실행할 수 있습니다.

#### Gradle 기반 빌드 및 실행
```bash
# 의존성 설치 및 빌드
./gradlew build

# 애플리케이션 실행
java -jar build/libs/saramIn-crawling-0.0.1-SNAPSHOT.jar
