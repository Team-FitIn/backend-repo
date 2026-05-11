package team.fitin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarmentJsonDto {
    private Long id;

    private String name;

    @JsonProperty("img_url")
    private String imgUrl;

    @JsonProperty("link")
    private String link;
    private String brand;
    private String categoryMain;
    private String categorySub;
}
