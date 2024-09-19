# 🚚Copang
스파르타 코딩클럽 내일배움캠프에서 주관한 대규모 AI 시스템 설계 프로젝트의 서버 코드입니다. DDD와 MSA Architecture로 구성되어 서비스의 확장성과 일관성을 염두에 두었습니다.
## 프로젝트 소개
일상생활에서 접할 수 있는 물류 관리 및 배송 시스템을 개발하면서 발생할 수 있는 문제와 해결방안을 찾는 것이 목표
## 개발 기간
- 2024.09.05 ~ 2024.09.19 (14일)
## 기술 스택
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
