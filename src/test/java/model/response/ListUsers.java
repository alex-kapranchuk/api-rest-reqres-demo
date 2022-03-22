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
public class ListUsers {
	private Integer perPage;
	private Integer total;
	private List<UserData> data;
	private Integer page;
	private Integer totalPages;
	private Support support;
}