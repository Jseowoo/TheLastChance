package the.last.chance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseNaverSearchDTO {

    @JsonProperty("total")
    private String total;

    @JsonProperty("start")
    private String start;

    @JsonProperty("display")
    private String display;

    @JsonProperty("lastBuildDate")
    private String lastBuildDate;

    @JsonProperty("items")
    private List<Item> items;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String title;
        private String description;
        private String link;
        private String bloggername;
        private String bloggerlink;
        private String postdate;
    }
}
