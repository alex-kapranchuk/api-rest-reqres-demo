package model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListResource {
    private Integer perPage;
    private Integer total;
    private List<ResourceData> data; // one different from ListUsers class
    private Integer page;
    private Integer totalPages;
    private Support support;
}
