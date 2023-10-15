package cn.linshenkx.halosyncserver.model.dto2.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDTO {
    private CategorySpecDTO spec = new CategorySpecDTO();
    private CategoryStatusDTO status = new CategoryStatusDTO();
    private String apiVersion = "content.halo.run/v1alpha1";
    private String kind = "Category";
    private CategoryMetadataDTO metadata = new CategoryMetadataDTO();
}
