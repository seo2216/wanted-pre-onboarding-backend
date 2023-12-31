package wanted.backend.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.backend.board.dto.BoardDTO;
import wanted.backend.user.UserEntity;


@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public BoardEntity save(BoardDTO boardDTO, UserEntity user){
        
        // 1. boardDto -> boardEntity로 변환
        BoardEntity boardEntity = toEntity(boardDTO,user);

        //2. save
        boardRepository.save(boardEntity);

        return boardRepository.findByBoardNo(boardEntity.getBoardNo());
    }
    @Transactional(readOnly = true)
    public BoardEntity getBoardById(Integer boardNo) {
        return boardRepository.findByBoardNo(boardNo);
    }

    @Transactional(readOnly = true)
    public Page<BoardEntity> getAllBoardsPaged(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public static BoardEntity toEntity(final BoardDTO dto, UserEntity user){

        return BoardEntity.builder()
                .boardNo(dto.getBoardNo())
                .userEntity(user)
                .writer(user.getUserEmail())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdDatetime(dto.getCreatedDatetime())
                .build();
    }
    @Transactional
    public BoardEntity update(Integer no, BoardDTO boardDTO){
        BoardEntity board = boardRepository.findByBoardNo(no);
        board.update(boardDTO.getTitle(), boardDTO.getContent());

        return board;
    }

    public void delete(Integer no){
        BoardEntity board = boardRepository.findByBoardNo(no);
        boardRepository.delete(board);
    }

    public static class BoardNotFoundException extends RuntimeException {
        public BoardNotFoundException(String message) {
            super(message);
        }
    }
}
