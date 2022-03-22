package model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true) // - ignore variable what we are not used
public class UserReg {
    private String email;
    private String password;
}
