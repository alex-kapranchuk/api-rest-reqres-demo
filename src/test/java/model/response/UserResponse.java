package model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String name;
    private String job;
    //    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'I'hh:mm:ss.SSSZ") //"createdAt": "2022-03-04T16:02:00.048Z"
//    @JsonDeserialize(using = DataDeserializer.class)
    private String updatedAt;
}
