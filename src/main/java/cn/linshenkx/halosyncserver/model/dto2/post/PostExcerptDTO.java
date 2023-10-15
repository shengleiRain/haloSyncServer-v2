package cn.linshenkx.halosyncserver.model.dto2.post;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostExcerptDTO {
    private Boolean autoGenerate = true;
    private String raw = "";
}
