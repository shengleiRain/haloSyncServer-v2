package cn.linshenkx.halosyncserver.model.dto2.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CategorySpecDTO {
    private String displayName = "";
    private String slug = "";
    private String template = "";
    private Integer priority = 0;
    private List<String> children;
}
