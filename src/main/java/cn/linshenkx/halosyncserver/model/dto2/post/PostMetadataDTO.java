package cn.linshenkx.halosyncserver.model.dto2.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PostMetadataDTO {
    private List<String> finalizers;
    private String name;
    private Integer version;
    private String creationTimestamp;
}
