# wanted-pre-onboarding-backend
## 과제
- 과제 1. 사용자 회원가입 엔드포인트
    - `POST /api/auth/user`
    - `{"userEmail" : "1234@asdf.com","password" : "1234568d"}`
- 과제 2. 사용자 로그인 엔드포인트
    - `POST /api/auth/login`
    - ` {"userEmail" : "1234@asdf.com","password" : "1234568d"}`
  
- 과제 3. 새로운 게시글을 생성하는 엔드포인트
    - `POST /api/boards`
    - ` {"title" : "제목","content" : "내용"}`
- 과제 4. 게시글 목록을 조회하는 엔드포인트
    - `GET /api/boards?page={5}&size={5}`
    - `/api/boards => defalut page = 0, size= 8` 

- 과제 5. 특정 게시글을 조회하는 엔드포인트
    - `GET /api/boards/{board_no}`

- 과제 6. 특정 게시글을 수정하는 엔드포인트
    - `PUT /api/boards/{board_no}`   
- 과제 7. 특정 게시글을 삭제하는 엔드포인트
    - `DELETE /api/boards/{board_no}`
