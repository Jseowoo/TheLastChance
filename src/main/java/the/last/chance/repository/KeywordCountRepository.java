package the.last.chance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import the.last.chance.dto.FavoriteKeywordDTO;
import the.last.chance.entity.KeywordCountEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface KeywordCountRepository extends JpaRepository<KeywordCountEntity, Long> {

    KeywordCountEntity findByKeyword(String keyword);

    @Query("SELECT k.keyword, COUNT(k) FROM KeywordCountEntity k GROUP BY k.keyword ORDER BY COUNT(k) DESC LIMIT 10")
    List<Object[]> countTop10KeywordGroupByKeywordOrderByCountDesc();

    //Converter 대신 아래의 함수를 추가하여 필요한 데이터만 FavoriteKeywordDTO에 넣고 return 해줍니다.
    default List<FavoriteKeywordDTO> findTop10FavoriteKeyword() {
        List<Object[]> resultList = countTop10KeywordGroupByKeywordOrderByCountDesc();
        List<FavoriteKeywordDTO> favoriteKeywords = new ArrayList<>();

        for (Object[] result : resultList) {
            FavoriteKeywordDTO favoriteKeyword = new FavoriteKeywordDTO(result);
            favoriteKeywords.add(favoriteKeyword);
        }

        return favoriteKeywords;
    }
}
