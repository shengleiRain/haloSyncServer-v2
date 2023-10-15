package cn.linshenkx.halosyncserver.httpclient;

import cn.linshenkx.halosyncserver.model.PageObject;
import cn.linshenkx.halosyncserver.model.PageObject2;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostDetailDTO;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostSimpleDTO;
import cn.linshenkx.halosyncserver.model.dto2.category.CategoryDTO;
import cn.linshenkx.halosyncserver.model.dto2.post.PostDTO;
import cn.linshenkx.halosyncserver.model.dto2.tags.TagDTO;
import cn.linshenkx.halosyncserver.model.enums.PostStatus;
import cn.linshenkx.halosyncserver.model.params.ConsolePostParam;
import cn.linshenkx.halosyncserver.model.params.PostContentParam;
import cn.linshenkx.halosyncserver.model.params.PostParam;
import cn.linshenkx.halosyncserver.model.params.PostQuery;
import cn.linshenkx.halosyncserver.model.support.BaseResponse;
import cn.linshenkx.halosyncserver.model.vo.PostDetailVO;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "halo", url = "${halo-sync.halo.url}", configuration = FeignConfiguration.class)
public interface HaloHttpClient {

    @GetMapping("/api/admin/posts/status/{status}")
    BaseResponse<PageObject<BasePostSimpleDTO>> pageByStatus(@PathVariable(name = "status") PostStatus status, @SpringQueryMap Pageable pageable, @RequestParam Boolean more);

    @GetMapping("/api/admin/posts/{postId}")
    BaseResponse<PostDetailVO> getByPostId(@PathVariable(name = "postId") Integer postId);

    @GetMapping("/api/admin/posts")
    BaseResponse<PageObject<BasePostSimpleDTO>> pageBy(@QueryMap Pageable pageable, @SpringQueryMap PostQuery postQuery, @RequestParam Boolean more);

    @DeleteMapping("/api/admin/posts")
    BaseResponse<List<BasePostSimpleDTO>> deletePermanentlyInBatch(@RequestBody List<Integer> ids);

    @PostMapping(value = "/api/admin/backups/markdown/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse<BasePostDetailDTO> backupMarkdowns(@RequestPart("file") MultipartFile file);

    @PutMapping("/api/admin/posts/{postId}")
    BaseResponse<PostDetailVO> updateBy(@RequestBody PostParam postParam,
                                        @PathVariable("postId") Integer postId,
                                        @RequestParam("autoSave") Boolean autoSave
    );

    @DeleteMapping("/api/admin/posts/{postId}")
    BaseResponse<Void> deletePermanently(@PathVariable("postId") Integer postId);

//    @RequestMapping(method = RequestMethod.POST, value = "/jobs/{jobId}/stop")
//    void stopJob(URI uri, @PathVariable("jobId") String jobId, StopJobBody stopJobBody);
//
//    @RequestMapping(method = RequestMethod.POST, value = "/jobs/{jobId}/savepoints")
//    TriggerResult savepoints(URI uri, @PathVariable("jobId") String jobId, SavepointBody savepointBody);
//
//    @RequestMapping(method = RequestMethod.GET, value = "/jobs/{jobId}/savepoints/{triggerid}")
//    TriggerQueryResult getSavepointTriggerResult(URI uri, @PathVariable("jobId") String jobId, @PathVariable("triggerid") String triggerid);


    @GetMapping("/apis/content.halo.run/v1alpha1/posts")
    PageObject2<PostDTO> pageOfPosts(@SpringQueryMap Pageable pageable);

    @GetMapping("/apis/content.halo.run/v1alpha1/posts/{name}")
    PostDTO getPost(@PathVariable String name);

    @PostMapping("/apis/content.halo.run/v1alpha1/posts")
    PostDTO savePost(@RequestBody PostDTO post);

    @DeleteMapping("/apis/content.halo.run/v1alpha1/posts/{name}")
    void deletePost(@PathVariable String name);

    @PostMapping("/apis/api.console.halo.run/v1alpha1/posts/{name}/content")
    void consoleSavePostContent(@RequestBody PostContentParam content, @PathVariable String name);

    @PostMapping("/apis/api.console.halo.run/v1alpha1/posts")
    PostDTO consoleSavePost(@RequestBody ConsolePostParam param);

    @PutMapping("/apis/api.console.halo.run/v1alpha1/posts/{name}")
    PostDTO updatePost(@RequestBody ConsolePostParam param, @PathVariable String name);

    @PutMapping("/apis/api.console.halo.run/v1alpha1/posts/{name}/recycle")
    void recyclePost(@PathVariable String name);

    @PutMapping("/apis/api.console.halo.run/v1alpha1/posts/{name}/publish")
    void publishPost(@PathVariable String name);

    @PutMapping("/apis/api.console.halo.run/v1alpha1/posts/{name}/unpublish")
    void unpublishPost(@PathVariable String name);

    @GetMapping("/apis/content.halo.run/v1alpha1/categories")
    PageObject2<CategoryDTO> pageOfCategories(@SpringQueryMap Pageable pageable);

    @PostMapping("/apis/content.halo.run/v1alpha1/categories")
    CategoryDTO saveCategory(@RequestBody CategoryDTO category);

    @GetMapping("/apis/content.halo.run/v1alpha1/tags")
    PageObject2<TagDTO> pageOfTags(@SpringQueryMap Pageable pageable);

    @PostMapping("/apis/content.halo.run/v1alpha1/tags")
    TagDTO saveTag(@RequestBody TagDTO category);
}
