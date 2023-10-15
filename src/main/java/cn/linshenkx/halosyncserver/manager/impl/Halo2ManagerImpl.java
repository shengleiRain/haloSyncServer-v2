package cn.linshenkx.halosyncserver.manager.impl;

import cn.linshenkx.halosyncserver.httpclient.HaloHttpClient;
import cn.linshenkx.halosyncserver.manager.Halo2Manager;
import cn.linshenkx.halosyncserver.model.PageObject2;
import cn.linshenkx.halosyncserver.model.dto2.category.CategoryDTO;
import cn.linshenkx.halosyncserver.model.dto2.post.PostDTO;
import cn.linshenkx.halosyncserver.model.dto2.post.PostMetadataDTO;
import cn.linshenkx.halosyncserver.model.dto2.post.PostSpecDTO;
import cn.linshenkx.halosyncserver.model.dto2.tags.TagDTO;
import cn.linshenkx.halosyncserver.model.params.ConsolePostParam;
import cn.linshenkx.halosyncserver.model.params.PostContentParam;
import cn.linshenkx.halosyncserver.prop.HaloGitProp;
import cn.linshenkx.halosyncserver.utils.HaloUtils;
import cn.linshenkx.halosyncserver.utils.MarkdownUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Halo2ManagerImpl implements Halo2Manager {
    @Resource
    private HaloHttpClient haloHttpClient;

    @Resource
    private HaloGitProp haloGitProp;

    @Override
    public List<PostDTO> getAllPosts() {
        PageObject2<PostDTO> data = haloHttpClient.pageOfPosts(Pageable.ofSize(Integer.MAX_VALUE));
        if (data != null) {
            return data.getItems();
        }
        return null;
    }

    @Override
    public void savePostByMarkdown(ArrayList<CategoryDTO> categoryDTOList, ArrayList<TagDTO> tagDTOList, String markdown, String filePath) {
        if (StringUtils.isBlank(markdown)) return;
        String fileContent = replacePostLink(markdown);

        ConsolePostParam param = new ConsolePostParam();
        PostDTO postDTO = param.getPost();
        PostContentParam contentParam = param.getContent();

        Map<String, List<String>> frontMatter = MarkdownUtils.getFrontMatter(fileContent);
        String raw = MarkdownUtils.removeFrontMatter(fileContent);
        String htmlContent = MarkdownUtils.renderHtml(fileContent);

        String title = MarkdownUtils.getFrontValue(frontMatter, "title");
        if (!StringUtils.isBlank(title)) {
            title = title.trim();
        }
        String date = MarkdownUtils.getFrontValue(frontMatter, "date");
        if (!StringUtils.isBlank(date)) {
            date = date.trim();
        }
        List<String> categories = MarkdownUtils.getFrontValueList(frontMatter, "categories");
        if (categories != null && !categories.isEmpty()) {
            categories = categories.stream().map(String::trim).collect(Collectors.toList());
        }
        List<String> tags = MarkdownUtils.getFrontValueList(frontMatter, "tags");
        if (tags != null && !tags.isEmpty()) {
            tags = tags.stream().map(String::trim).collect(Collectors.toList());
        }

        String slug = HaloUtils.toSlug(title);

        String haloName = HaloUtils.toMd5(filePath);

        //根据name查找post
        boolean isFindPost = false;
        try {
            PostDTO findPost = haloHttpClient.getPost(haloName);
            if (findPost != null) {
                postDTO = findPost;
                isFindPost = true;
            }
        } catch (Exception e) {
            log.info("post {} not find in halo.", haloName);
        }


        PostSpecDTO spec = postDTO.getSpec();
        spec.setTitle(title);
        spec.setCategories(categories);
        spec.setTags(tags);
        spec.setSlug(slug);

        PostMetadataDTO metadata = postDTO.getMetadata();
        metadata.setName(haloName);

        contentParam.setContent(htmlContent);
        contentParam.setRaw(raw);

        param.setPost(postDTO);
        param.setContent(contentParam);

        try {
            // save categories
            if (categories != null) {
                categories.forEach((item) -> {
                    CategoryDTO find = findCategoryByName(categoryDTOList, item);
                    if (find == null) {
                        CategoryDTO categoryDTO = new CategoryDTO();
                        categoryDTO.getMetadata().setName(item);
                        categoryDTO.getSpec().setDisplayName(item);
                        categoryDTO.getSpec().setSlug(HaloUtils.toSlug(item));
                        categoryDTO.getSpec().setPriority(0);
                        categoryDTO = haloHttpClient.saveCategory(categoryDTO);
                        categoryDTOList.add(categoryDTO);
                    }
                });
            }

            // save tags
            if (tags != null) {
                tags.forEach((item) -> {
                    TagDTO find = findTagByName(tagDTOList, item);
                    if (find == null) {
                        TagDTO tagDTO = new TagDTO();
                        tagDTO.getMetadata().setName(item);
                        tagDTO.getSpec().setDisplayName(item);
                        tagDTO.getSpec().setSlug(HaloUtils.toSlug(item));
                        tagDTO = haloHttpClient.saveTag(tagDTO);
                        tagDTOList.add(tagDTO);
                    }
                });
            }

            if (isFindPost) {
                // update post
                postDTO = haloHttpClient.updatePost(param, haloName);
            } else {
                // save post
                postDTO = haloHttpClient.consoleSavePost(param);
            }

            // publish post
            haloHttpClient.publishPost(postDTO.getMetadata().getName());

        } catch (Exception e) {
            log.error("《{}》 Publish failed, please try again", title);
        }

    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        PageObject2<CategoryDTO> categories = haloHttpClient.pageOfCategories(Pageable.ofSize(Integer.MAX_VALUE));
        if (categories != null) {
            return categories.getItems();
        }
        return null;
    }

    @Override
    public List<TagDTO> getAllTags() {
        PageObject2<TagDTO> tags = haloHttpClient.pageOfTags(Pageable.ofSize(Integer.MAX_VALUE));
        if (tags != null) {
            return tags.getItems();
        }
        return null;
    }

    @Override
    public void recyclePost(String name) {
        haloHttpClient.recyclePost(name);
        haloHttpClient.deletePost(name);
    }

    @Override
    public void renamePost(String oldPath, String newPath) {
        String oldName = HaloUtils.toMd5(oldPath);
        String newName = HaloUtils.toMd5(newPath);
        try{
            PostDTO postDTO = haloHttpClient.getPost(oldName);
            postDTO.getMetadata().setName(newName);
            haloHttpClient.savePost(postDTO);
        }catch (Exception e) {
            log.info("rename error: {}", e.getMessage());
        }
    }

    private CategoryDTO findCategoryByName(List<CategoryDTO> categoryDTOList, String displayName) {
        if (StringUtils.isBlank(displayName)) return null;
        return categoryDTOList.stream().filter((item) -> displayName.equals(item.getSpec().getDisplayName())).findFirst().orElse(null);
    }

    private TagDTO findTagByName(List<TagDTO> tagDTOList, String displayName) {
        if (StringUtils.isBlank(displayName)) return null;
        return tagDTOList.stream().filter((item) -> displayName.equals(item.getSpec().getDisplayName())).findFirst().orElse(null);
    }

    private String replacePostLink(String markdown) {
        if (StringUtils.isBlank(markdown)) return markdown;

        try {
            String pattern = "\\{% post_link (.+?) %}";

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(markdown);

            while (m.find()) {
                String match = m.group();
                String content = m.group(1);
                String[] split = content.trim().split(" ");
                String fileMd5 = HaloUtils.toMd5("source/_posts/" + split[0] + ".md");
                String filename = split[1];
                String replace = "[%s](%s)".formatted(filename, haloGitProp.getUrl() + "/archives/" + fileMd5);
                markdown = markdown.replace(match, replace);
            }
        } catch (Exception e) {
            return markdown;
        }

        return markdown;
    }
}
