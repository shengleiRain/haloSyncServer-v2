package cn.linshenkx.halosyncserver.model.params;

import cn.linshenkx.halosyncserver.model.dto2.post.PostDTO;
import lombok.Data;

@Data
public class ConsolePostParam {
    private PostDTO post = new PostDTO();
    private PostContentParam content = new PostContentParam();
}
