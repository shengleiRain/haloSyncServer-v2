package cn.linshenkx.halosyncserver.model.dto2.post;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostConditionsDTO {
    private String type;
    private String status;
    private String lastTransitionTime;
    private String message;
    private String reason;
}
