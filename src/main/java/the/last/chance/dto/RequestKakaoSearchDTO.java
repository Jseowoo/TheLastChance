package the.last.chance.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestKakaoSearchDTO {

    @NotBlank(message = "query parameter required")
    private String query;

    @Min(value = 1, message = "size is less than min")
    @Max(value = 50, message = "size is more than max")
    private Integer page = 1, size = 10;

    private Boolean search = true;

    private String sort = "accuracy";

    public void setSort(String sort) {
        this.sort = !(sort.equals("accuracy") || sort.equals("recency")) ? "accuracy" : "recency";
    }
}
