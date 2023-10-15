package cn.linshenkx.halosyncserver.model.dto2.tags;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TagDTO {
    private TagSpecDTO spec = new TagSpecDTO();
    private TagStatusDTO status = new TagStatusDTO();
    private String apiVersion = "content.halo.run/v1alpha1";
    private String kind = "Tag";
    private TagMetadataDTO metadata = new TagMetadataDTO();
}
