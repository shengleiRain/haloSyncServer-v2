package cn.linshenkx.halosyncserver.model.dto2.tags;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class TagMetadataDTO {
    private String generateName;
    private List<String> finalizers;
    private String name;
    private Integer version;
    private String creationTimestamp;
}
