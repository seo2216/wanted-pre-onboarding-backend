package wanted.backend.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {


    BoardEntity findByBoardNo(Integer boardNo);
}
