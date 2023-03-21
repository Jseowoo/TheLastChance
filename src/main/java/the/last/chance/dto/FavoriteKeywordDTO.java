package the.last.chance.dto;

import lombok.Data;

@Data
public class FavoriteKeywordDTO {

    private String keyword;
    private Long count;

    public FavoriteKeywordDTO(Object[] result) {
        this.keyword = (String) result[0];
        this.count = (Long) result[1];
    }

}
