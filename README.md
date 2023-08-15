# wanted-pre-onboarding-backend
## 과제
- 과제 1. 사용자 회원가입 엔드포인트
  `http://localhost:8080/api/auth/user`
  `{
    "userEmail" : "1234@asdf.com",
    "password" : "1234568d"
  }`
- 과제 2. 사용자 로그인 엔드포인트
  `http://localhost:8080/api/auth/login`
 ` {
      "userEmail" : "1234@asdf.com",
      "password" : "1234568d"
  }`
  
- 과제 3. 새로운 게시글을 생성하는 엔드포인트
  `http://localhost:8080/api/boards`
 ` {
    "title" : "제목",
    "content" : "내용용"
  }`
- 과제 4. 게시글 목록을 조회하는 엔드포인트
  - `http://localhost:8080/api/boards?page={5}&size={5}`
  - `http://localhost:8080/api/boards` => defalut page = 0, size= 8

- 과제 5. 특정 게시글을 조회하는 엔드포인트
- `http://localhost:8080/api/boards/{board_no}`

- 과제 6. 특정 게시글을 수정하는 엔드포인트
  - [ ]게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
  - [ ]게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
- 과제 7. 특정 게시글을 삭제하는 엔드포인트
  - [ ]게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
  - [ ]게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.
