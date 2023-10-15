package cn.linshenkx.halosyncserver.model.dto2.tags;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TagStatusDTO {
    private String permalink;
    private Integer visiblePostCount;
    private Integer postCount;
}
