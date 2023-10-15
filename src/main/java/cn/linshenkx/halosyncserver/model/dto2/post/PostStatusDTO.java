package cn.linshenkx.halosyncserver.model.dto2.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PostStatusDTO {
    private String phase;
    private List<PostConditionsDTO> conditions;
    private String permalink;
    private String excerpt;
    private Boolean inProgress;
    private List<String> contributors;
    private String lastModifyTime;
}
