package team.fitin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductJsonDto {
    private Long id;

    private String name;

    @JsonProperty("img_url")
    private String imgUrl;

    private String link;
}
