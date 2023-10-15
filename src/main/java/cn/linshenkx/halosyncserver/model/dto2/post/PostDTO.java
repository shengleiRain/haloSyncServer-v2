package cn.linshenkx.halosyncserver.model.dto2.post;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostDTO {
    private PostSpecDTO spec = new PostSpecDTO();
    private PostStatusDTO status;
    private String apiVersion = "content.halo.run/v1alpha1";
    private String kind = "Post";
    private PostMetadataDTO metadata = new PostMetadataDTO();
}
