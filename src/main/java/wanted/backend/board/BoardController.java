package wanted.backend.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wanted.backend.board.dto.BoardDTO;
import wanted.backend.board.dto.BoardMainDto;
import wanted.backend.user.UserEntity;
import wanted.backend.user.UserRepository;
import wanted.backend.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/api/boards")
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> postBoard(@AuthenticationPrincipal String userId
            ,@RequestBody BoardDTO boardDTO){
        try{
            //1. UserEntity 조회
            UserEntity userEntity = userRepository.findById(Integer.parseInt(userId))
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            //2. 게시판 저장
            BoardEntity savedBoard = boardService.save(boardDTO, userEntity);

            //3. 반환할 DTO
            BoardDTO resposneDto = BoardDTO.builder()
                    .boardNo(savedBoard.getBoardNo())
                    .userId(savedBoard.getUserEntity().getId())
                    .writer(savedBoard.getWriter())
                    .title(savedBoard.getTitle())
                    .content(savedBoard.getContent())
                    .createdDatetime(savedBoard.getCreatedDatetime())
                    .build();

            return ResponseEntity.ok().body(resposneDto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBoards(@AuthenticationPrincipal String userId
    ,@RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "8") int size){
        Sort sortObj = Sort.by(Sort.Direction.fromString("DESC"), "boardNo");
        Pageable boardPageable = PageRequest.of(page, size, sortObj);
        Page<BoardEntity> searchedBoardPage;

        searchedBoardPage = boardService.getAllBoardsPaged(boardPageable);

        List<BoardEntity> boardEntities = searchedBoardPage.getContent();

        if (searchedBoardPage == null || searchedBoardPage.isEmpty()) {
            // 데이터가 없는 경우에 대한 처리 로직 추가
            return ResponseEntity.notFound().build();
        }

        List<BoardDTO> boardDtos = boardEntities.stream()
                .map(boardEntity -> BoardDTO.builder()
                        .boardNo(boardEntity.getBoardNo())
                        .title(boardEntity.getTitle())
                        .createdDatetime(boardEntity.getCreatedDatetime())
                        .writer(boardEntity.getWriter())
                        .content(boardEntity.getContent())
                        .userId(boardEntity.getUserEntity().getId())
                        .build())
                .collect(Collectors.toList());

        BoardMainDto<BoardDTO> result = new BoardMainDto<>(
                boardDtos,
                searchedBoardPage.getNumber(),
                searchedBoardPage.getTotalPages()
        );
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<?> findBoardById(@AuthenticationPrincipal String userId,
        @PathVariable Integer boardNo){
        try{
            BoardEntity boardEntity = boardService.getBoardById(boardNo);
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardNo(boardEntity.getBoardNo())
                    .title(boardEntity.getTitle())
                    .createdDatetime(boardEntity.getCreatedDatetime())
                    .writer(boardEntity.getWriter())
                    .content(boardEntity.getContent())
                    .userId(boardEntity.getUserEntity().getId())
                    .build();
            return ResponseEntity.ok().body(boardDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{boardNo}")
    public ResponseEntity<?> update(@PathVariable Integer boardNo,
                                    @AuthenticationPrincipal String userId,
                                    @RequestBody BoardDTO boardDTO){
        try{
            //1. 작성자 확인
            BoardEntity boardEntity = boardService.getBoardById(boardNo);
            if(boardEntity == null){
                throw new BoardService.BoardNotFoundException("Board with number " + boardNo + " not found");
            }

            boolean isEditable = boardEntity.getUserEntity().getId().equals(Integer.parseInt(userId));
            if(!isEditable){
                throw new IllegalArgumentException("You are not authorized to update this board.");
            }
            BoardEntity updateBoard = boardService.update(boardNo,boardDTO);

            BoardDTO dto = BoardDTO.builder()
                    .boardNo(updateBoard.getBoardNo())
                    .title(updateBoard.getTitle())
                    .createdDatetime(updateBoard.getCreatedDatetime())
                    .writer(updateBoard.getWriter())
                    .content(updateBoard.getContent())
                    .userId(updateBoard.getUserEntity().getId())
                    .build();
            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
