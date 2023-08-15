# wanted-pre-onboarding-backend - 지원자 김윤설
## 과제 제출 필수 사항
- [X] 지원자의 성명
- [X] 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
- [ ] 데이터베이스 테이블 구조
- [ ] 구현한 API의 동작을 촬영한 데모 영상 링크
- [ ] 구현 방법 및 이유에 대한 간략한 설명
- [X] API 명세(request/response 포함)
  
## 애플리케이션의 실행 방법과 API 명세
- 과제 1. 사용자 회원가입 엔드포인트 `POST /api/auth/user`
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
- 과제 2. 사용자 로그인 엔드포인트 `POST /api/auth/login`
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
  
- 과제 3. 새로운 게시글을 생성하는 엔드포인트 `POST /api/boards`
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

  
- 과제 4. 게시글 목록을 조회하는 엔드포인트 `GET /api/boards?page={5}&size={5}`, `/api/boards => defalut page = 0, size= 8` 
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

- 과제 5. 특정 게시글을 조회하는 엔드포인트 `GET /api/boards/{board_no}`
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

- 과제 6. 특정 게시글을 수정하는 엔드포인트 `PUT /api/boards/{board_no}`
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
- 과제 7. 특정 게시글을 삭제하는 엔드포인트 `DELETE /api/boards/{board_no}`
    ```
    [request]
    
    [response] Board deleted successfully.
    ```
