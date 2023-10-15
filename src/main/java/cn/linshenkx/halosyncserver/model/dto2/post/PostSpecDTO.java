package cn.linshenkx.halosyncserver.model.dto2.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PostSpecDTO {
    private String title = "";
    private String slug = "";
    private String releaseSnapshot;
    private String headSnapshot;
    private String baseSnapshot;
    private String owner;
    private String template = "";
    private String cover = "";
    private Boolean deleted = false;
    private Boolean publish = true;
    private String publishTime;
    private Boolean pinned = false;
    private Boolean allowComment = true;
    private String visible = "PUBLIC";
    private Integer priority = 0;
    private PostExcerptDTO excerpt = new PostExcerptDTO();
    private List<String> categories;
    private List<String> tags;
}
