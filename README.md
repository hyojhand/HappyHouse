<h1 align="center">HappyHouse</h1>

### 목차

---
1. [프로젝트 소개](#프로젝트소개)
2. [Project Period](#ProjectPeriod)
3. [Contributors](#Contributors)
4. [사용 스택](#사용스택)
5. [프로젝트 세팅](#프로젝트세팅)
6. [ERD](#ERD)
7. [핵심 기능](#핵심기능)
8. [상세 페이지](#상세페이지)

## 프로젝트&nbsp;소개
- SSAFY 7기 1학기 Final 프로젝트 ( 최우수 프로젝트 수상🏅 )

아파트 매물 정보와 주변 상권정보, 교통상황, 로드뷰를 통해 사용자에게 주거환경에 대한 생생한 경험을 제공하고,  
커뮤니티 탭과 쪽지 기능을 통해 사용자간의 소통 서비스 제공을 목표로 합니다.

시연영상 : 링크

## Project&nbsp;Period

2022.05.19 ~ 2022.05.26

## Contributors

| 팀원   | 역할                              | 비고           | 깃헙                              |
| ------ | --------------------------------- | -------------- | --------------------------------- |
| 최지원 | 팀장, 백엔드, 프론트엔드 |  | https://github.com/CHOIJXWXN  |
| 손효재 | 백엔드, 프론트엔드 |   | https://github.com/hyojhand  |

## 사용&nbsp;스택

| 용도              | 스택                                                         | 버전               |
| ----------------- | ------------------------------------------------------------ | ------------------ |
| 개발 언어           | <img src="https://img.shields.io/badge/Java-007396?style=plastic&logo=Java&logoColor=white"> |   |
| 서버 설계         | <img src="https://img.shields.io/badge/SpringBoot-339933?style=plastic&logo=SpringBoot&logoColor=white"> | sts-3.9.14.RELEASE |
| DB                | <img src="https://img.shields.io/badge/MySQL-4169E1?style=plastic&logo=MySQL&logoColor=white"> | 8.0.x / 8.0.28   |
| JS 편집           | <img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=plastic&logo=Visual Studio Code&logoColor=white"> | v1.67  |
| UI 개발           | <img src="https://img.shields.io/badge/Vue.js-4FC08D?style=plastic&logo=Vue.js&logoColor=white"> |                    |
| 형상관리          | <img src="https://img.shields.io/badge/git-F05032?style=plastic&logo=git&logoColor=white">  |                    |


## 프로젝트&nbsp;세팅

```
npm install
npm run serve
```
### Comments
- Booststrap 버전을 **4.5.3**으로 변경 후 npm install

## ERD

<img src="https://user-images.githubusercontent.com/87989933/170809116-45145dda-8dd8-4e22-a8ca-7367fb6285c5.png" width="900" height="600"/>

## 핵심&nbsp;기능

- 메인페이지
  - 아파트 관련 최신 뉴스 크롤링
  - 검색량 및 북마크 통계를 통한 트렌드 분석
  - 회원의 최다 선택지역 추천 알림

- 회원 관리 
  - JWT(JSON Web Token) 이용
  - 회원가입 및 로그인, 로그아웃

- 아파트 검색 정보
  - 지역 선택시 해당 위치의 카카오맵 API 활용
  - 아파트 매물 목록을 마커로 표시
  - 주변 인프라 정보(상권, 교통, 자전거도로 등) 필터 선택시 정보 표시
  - 로드뷰 기능 추가

- 아파트 관련 정보
  - 아파트 검색결과 리스트 표시
  - 리스트 정렬 기준(이름,건축년도,가격,평수)으로 정렬하여 확인
  - 선택 아파트 반경내의 상권정보 평가
  - 관심지역 매물 북마크 기능

- 게시판
  - 커뮤니티 게시판 등록, 수정, 삭제
  - 댓글 등록, 수정, 삭제
  - 게시글 좋아요 기능
  - 회원간의 쪽지 기능

- 마이페이지
  - 회원정보 조회 및 수정,탈퇴
  - 쪽지함 (쪽지확인, 답장, 삭제)
  - 작성 게시글, 좋아요한 게시글 목록 & 바로가기 
  - 작성한 댓글 & 바로가기

- 관리자 기능
  - 전체 회원정보 관리
  - 전체 검색 결과 통계 확인
  - 전체 북마크 결과 통계 확인

- 에러페이지
  - 에러페이지 처리

## 상세&nbsp;페이지

- 메인 페이지

  - 뉴스 크롤링, 검색량 및 북마크 통계
  
  ![메인화면](/happyhouse_document/img/메인화면.png)
  
  - 최다 선택지역 추천 알림
  
  ![메인_알림](/happyhouse_document/img/메인_알림.png)
  
  - Navibar

  ![메인_Navibar](/happyhouse_document/img/메인_Navibar.png)


- 회원 관리

  - 회원가입 및 로그인, 로그아웃
  
  ![회원관리_회원가입_로그인](/happyhouse_document/img/회원관리_회원가입_로그인.png)


- 아파트 검색 정보

  - 아파트 매물 목록, 주변상권 표시
  
  ![아파트_검색_맵](/happyhouse_document/img/아파트_검색_맵.png)

  
  - 주변 교통, 자전거도로 정보 및 로드뷰 기능
  
  ![아파트 교통정보_로드뷰](/happyhouse_document/img/아파트_교통정보_로드뷰.png)


- 아파트 관련 정보

  - 아파트 검색결과 및 상세정보
  
  ![아파트_상세정보](/happyhouse_document/img/아파트_상세정보.png)

  - 관심지역 매물 북마크 기능
  
  ![아파트_북마크관리](/happyhouse_document/img/아파트_북마크관리.png)


- 게시판

  - 게시판 목록 및 댓글, 좋아요 기능
  
  ![게시판_메인_좋아요댓글](/happyhouse_document/img/게시판_메인_좋아요댓글.png)

  - 커뮤니티 게시판 등록, 수정, 삭제

  ![게시판_작성_수정](/happyhouse_document/img/게시판_작성_수정.png)


  - 회원간의 쪽지 기능
  
  ![게시판_쪽지작성](/happyhouse_document/img/게시판_쪽지작성.png)


- 마이페이지

  - 회원정보, 쪽지함
  
  ![마이페이지_쪽지함](/happyhouse_document/img/마이페이지_쪽지함.png)
  
  - 작성 게시글, 댓글, 좋아요한 게시글 목록 & 바로가기 
  
  ![마이페이지_글_댓글_좋아요](/happyhouse_document/img/마이페이지_글_댓글_좋아요.png)


- 관리자 기능

  ![관리자페이지](/happyhouse_document/img/관리자페이지.png)

- 에러페이지

  ![에러페이지](/happyhouse_document/img/에러페이지.png)

