DB 테이블 정보 추출후 QUARTZ 스케줄러를 통한 다양한 포맷의 파일 생성을 목표로 진행한 프로젝트 입니다.

[데이터 흐름도]
![1](https://github.com/alfhaos/TableExtraction/assets/87509332/1c8456bc-58f0-4b0a-81c0-284e782a2577)
![Untitled](https://github.com/alfhaos/TableExtraction/assets/87509332/1155bd44-80f5-4dd5-be2c-3cc6ea1ec261)

- 스케줄러 흐름도
![2](https://github.com/alfhaos/TableExtraction/assets/87509332/3cfec590-b0ab-4b4d-ad1c-2e24c06e68d9)
![Untitled (1)](https://github.com/alfhaos/TableExtraction/assets/87509332/2cbeeddb-8fd5-403e-b3d5-8f61d117a1b1)

[목표기능]
- DB테이블 정보 추출
- 추출정보 엑셀 파일로 생성
- DB에 연결하여 스키마 및 테이블 선택
- 추출정보를 선택하여 해당 항목만 출력하는 Filter 기능
- 추출 포맷선택(CSV, JSON, XML)

[기간]
프로젝트 진행 기간 (2022.6 ~ 2022.7)

[사용 기술 및 도구]
- Frontend
    - jsp, ajax, jquery, jstl
- Backend
    - RestApi, quartz, spring security, myBatis, Apache POI
- Frame work
    - Spring boot
- DataBase
    - Oracle
