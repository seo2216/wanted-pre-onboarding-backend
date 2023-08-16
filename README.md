# wanted-pre-onboarding-backend - 지원자 김윤설
## 과제 제출 필수 사항
- [X] 지원자의 성명
- [X] 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
- [X] 데이터베이스 테이블 구조
- [X] 구현한 API의 동작을 촬영한 데모 영상 링크
- [X] 구현 방법 및 이유에 대한 간략한 설명
- [X] API 명세(request/response 포함)

## 추가 사항 
- [ ] 통합 테스트 또는 단위 테스트 코드를 추가한 경우
- [ ] docker compose를 이용하여 애플리케이션 환경을 구성한 경우 (README.md 파일에 docker-compose 실행 방법 반드시 기입)
- [ ] 클라우드 환경(AWS, GCP)에 배포 환경을 설계하고 애플리케이션을 배포한 경우 (README.md 파일에 배포된 API 주소와 설계한 AWS 환경 그림으로 첨부)

## AWS 배포
- IP : http://13.124.152.24

## 구현 방법 및 이유에 대한 간략한 설명
- SpringBoot + JPA 를 사용해서 구현함
  - sql에 중심이 아닌 객체지향적으로 서비스 구현
- Controller, Service, Repository 나눠서 구현하여 역할별로 분리하고, 중복코드를 제거하고, 확장성과 재사용성 높임
- Entity와 DTO를 분리하여 Entity의 setter를 없애고, 로직에 필요한 데이터만 선별해서 DTO를 생성함
- DTO와 Builder Pattern를 사용하여 필요한 데이터만 설정하여 유연성 높임

    ex) `BoardUpdateRequestDto`, `boardDTO`
- 게시글 수정 시 `더티 체킹(Dirty Checking)` 활용함
    1. 영속성 컨텍스트 = 엔티티를 영구 저장하는 환경
    2. JPA의 EntityManager가 활성화된 상태로 트랜잭션 내에서 데이터베이스의 데이터를 가져옴 (영속성 컨텍스트 유지 상태)
    3. 영속성 컨텍스트 유지 상태 해당 데이터의 값을 변경할 경우, 트랜잭션이 종료되는 시점에 해당 테이블에 변경분을 반영
       -  Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요 X

## 애플리케이션의 실행 방법과 API 명세
- 과제 1. 사용자 회원가입 엔드포인트
  
  `POST /api/auth/user`
    ```
    [request]
    { "userEmail" : "1234@asdf.com"
     ,"password" : "1234568d"}
    
    [response]
    { "token": null,
       id": 40,
       "userEmail": "12345@asdf.com",
       "password": null }
    ```
- 과제 2. 사용자 로그인 엔드포인트

   `POST /api/auth/login`
    ```
    [request]
    {"userEmail" : "1234@asdf.com"
      ,"password" : "1234568d"}
    
    [response]
    { "token":"OTIxMTgyMjAsImV4cCI6MTY5MjZdcY_buuSzy4pizDU1-OGCZfvhS_irVXU-S0voFJQJQABAnSbaFaW..asd",
    "id": 39,
    "userEmail": "123@asdf.com",
    "password": null}
    ```
  
- 과제 3. 새로운 게시글을 생성하는 엔드포인트

  `POST /api/boards`
    ```
    [request]
    {"title" : "제목","content" : "내용"}
    
    [response]
    {"boardNo": 41,
    "userId": 39,
    "writer": "123@asdf.com",
    "title": "제목",
    "content": "내용",
    "createdDatetime": "2023-08-15T16:54:55.983+00:00" }
    ```

  
- 과제 4. 게시글 목록을 조회하는 엔드포인트

  `GET /api/boards?page={5}&size={5}`, `/api/boards => defalut page = 0, size= 8` 
    ```
    [request]
    
    [response]
    {
        "data": [
            {
                "boardNo": 41,
                "userId": 39,
                "writer": "123@asdf.com",
                "title": "제목2",
                "content": "내용용3",
                "createdDatetime": "2023-08-15T16:54:55.983+00:00"
            },
            ...
            ,{
                "boardNo": 32,
                "userId": 6,
                "writer": "1234@asdf.com",
                "title": "제목2",
                "content": "내용용3",
                "createdDatetime": "2023-08-15T14:57:39.340+00:00"
            }
        ],
        "currentPage": 0,
        "totalPages": 4
    }
    ```

- 과제 5. 특정 게시글을 조회하는 엔드포인트

  `GET /api/boards/{board_no}`
    ```
    [request]
    
    [response]
    {
        "boardNo": 15,
        "userId": 6,
        "writer": "1234@asdf.com",
        "title": "제목",
        "content": "내용용",
        "createdDatetime": "2023-08-15T14:57:28.339+00:00"
    }
    ```

- 과제 6. 특정 게시글을 수정하는 엔드포인트

  `PUT /api/boards/{board_no}`
   ```
    [request]
    {"title" : "변경",
    "content": "변경"}

    [response]
    {
        "boardNo": 41,
        "userId": 39,
        "writer": "123@asdf.com",
        "title": "변경",
        "content": "변경",
        "createdDatetime": "2023-08-15T16:54:55.983+00:00"
    }
  ```
- 과제 7. 특정 게시글을 삭제하는 엔드포인트

  `DELETE /api/boards/{board_no}`
    ```
    [request]
    
    [response] Board deleted successfully.
    ```

## 데모 영상 링크
https://drive.google.com/drive/folders/1Smk4UTxItW1XN_1mFlGimLXex4HE-Npb?usp=sharing

## 데이터베이스 테이블 구조
<img width="575" alt="db 테이블 구조" src="https://github.com/seo2216/wanted-pre-onboarding-backend/assets/111838497/97104bdd-941b-44cb-8f86-6416f1371dbd">
