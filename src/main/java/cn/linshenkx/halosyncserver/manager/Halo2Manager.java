package cn.linshenkx.halosyncserver.manager;

import cn.linshenkx.halosyncserver.model.dto2.category.CategoryDTO;
import cn.linshenkx.halosyncserver.model.dto2.post.PostDTO;
import cn.linshenkx.halosyncserver.model.dto2.tags.TagDTO;

import java.util.ArrayList;
import java.util.List;

public interface Halo2Manager {
    List<PostDTO> getAllPosts();


    void savePostByMarkdown(ArrayList<CategoryDTO> categoryDTOList, ArrayList<TagDTO> tagDTOList, String markdown, String filePath);

    List<CategoryDTO> getAllCategories();

    List<TagDTO> getAllTags();

    void recyclePost(String name);

    void renamePost(String oldPath, String newPath);
}
