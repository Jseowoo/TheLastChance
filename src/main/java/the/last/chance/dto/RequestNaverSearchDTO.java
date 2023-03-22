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
public class RequestNaverSearchDTO {

    @NotBlank(message = "query parameter required")
    private String query;

    @Min(value = 1, message = "size is less than min")
    @Max(value = 1000, message = "size is more than max")
    private Integer start = 1;

    @Min(value = 1, message = "size is less than min")
    @Max(value = 100, message = "size is more than max")
    private Integer display = 10;

    private Boolean search = true;

    private String sort = "sim";

    public void setSort(String sort) {
        this.sort = !(sort.equals("sim") || sort.equals("date")) ? "sim" : "date";
    }
}
