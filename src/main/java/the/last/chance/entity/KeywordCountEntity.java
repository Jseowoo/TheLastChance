package the.last.chance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class KeywordCountEntity {

    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Getter @Setter
    private String keyword;

    @Getter
    private LocalDateTime date;

    public void setDate(){
        this.date = LocalDateTime.now();
    }

    //검색한 클라이언트 정보(위치, ip 등)
    @Getter @Setter
    private String locale, ip;
}
