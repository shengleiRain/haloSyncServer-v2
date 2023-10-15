package cn.linshenkx.halosyncserver.model.dto2.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryStatusDTO {
    private String permalink;
    private Integer postCount;
    private Integer visiblePostCount;
}
