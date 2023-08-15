package wanted.backend.board;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import wanted.backend.user.UserEntity;

import javax.persistence.*;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Integer boardNo;
    private Integer userId;
    private String writer;
    private String title;
    private String content;
    private Date createdDatetime;

}
