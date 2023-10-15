package cn.linshenkx.halosyncserver.model.params;

import lombok.Data;

/**
 * Post content param.
 *
 * @author johnniang
 */
@Data
public class PostContentParam {
    private String raw;
    private String content;
    private String rawType = "markdown";
}
