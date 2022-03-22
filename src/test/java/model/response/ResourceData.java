package model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResourceData {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;
    private Support support;
}
