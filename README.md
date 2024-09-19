# 🚚Copang
스파르타 코딩클럽 내일배움캠프에서 주관한 대규모 AI 시스템 설계 프로젝트의 서버 코드입니다. DDD와 MSA Architecture로 구성되어 서비스의 확장성과 일관성을 염두에 두었습니다.
## 프로젝트 소개
일상생활에서 접할 수 있는 물류 관리 및 배송 시스템을 개발하면서 발생할 수 있는 문제와 해결방안을 찾는 것이 목표
## 개발 기간
- 2024.09.05 ~ 2024.09.19 (14일)
## 기술 스택
<img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.3.3-515151?style=for-the-badge">
<img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">
<img src="https://img.shields.io/badge/PostgreSQL-4479A1?style=for-the-badge&logo=PostgreSQL&logoColor=white">
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
- Language: Java 17
- Framework: Spring boot 3.3
- Repository: PostgreSQL, Redis
- Build Tool: Gradle 8.10
  
## 팀원 소개
|  | 김지희 | 이지선 | 송기찬 |
| --- | --- | --- | --- |
| 역할 | 서비스 디스커버리, 상품, 주문, 배송, 슬랙 | 유저, 게이트웨이, 업체 | 허브, AI |
## 서비스 구성
| 서비스 이름 | 역할 | 포트 |
| --- | --- | --- |
| eureka | 서비스 디스커버리 | 19090 |
| gateway | 게이트웨이 | 19091 |
| user | 유저 | 19092 |
| hub | 허브 | 19093 |
| company | 업체 | 19094 |
| product | 상품 | 19095 |
| order | 주문 | 19096 |
| delivery | 배송 | 19097 |
| AI | Gemini-api | 19098 |
| message | slack | 19099 |
## 인프라 구성
![image](https://github.com/user-attachments/assets/703f5566-6e9a-44d8-a945-a53af6a8c8b7)

## ERD
[![image](https://github.com/user-attachments/assets/9c457e07-a1fb-4c33-a4de-363989706b68)](https://www.erdcloud.com/d/YHoPKNNe22t4d23zP)
## 트러블 슈팅
### 스프링 부트 애플리케이션이 구동될 때 코드를 실행하는 방법
Hub 같은 경우에는 팀원들이 어플리케이션을 테스트하기 위해 고정된 데이터가 필요했다. 이를 Spring Boot 테스트로 작성할 수도 있었지만, 어플리케이션이 실행되면 더미데이터를 집어넣는 방식으로 해결하였다.
``` java
@Component
@RequiredArgsConstructor
public class HubInitializer {

    private final HubRepository hubRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
      // dummy data save code
    }
}
```
