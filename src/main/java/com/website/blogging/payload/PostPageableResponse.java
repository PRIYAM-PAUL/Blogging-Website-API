package com.website.blogging.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostPageableResponse {

	private List<PostDto> posts;	
	private int pageNumber;
	private int pageSize;
	private long pageElement;
	private boolean lastPage;
}
