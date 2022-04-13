package codes.recursive.searchdemo.controller;

import codes.recursive.searchdemo.domain.BlogPost;
import codes.recursive.searchdemo.service.BlogPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
@Slf4j
public class SearchController {

    private final BlogPostService blogPostService;

    @Autowired
    public SearchController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping({"/blogPost"})
    public ResponseEntity<Map<String, Object>> saveBlogPost(@RequestBody BlogPost blogPost) {
        blogPostService.saveIndex(blogPost);
        return new ResponseEntity<Map<String, Object>>(
                Map.of(
                        "blogPost", blogPost,
                        "saved", true
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping({"/blogPost"})
    public ResponseEntity deleteBlogPost(@RequestParam String id) {
        Optional<BlogPost> blogPost = blogPostService.findById(id);
        if(blogPost.isPresent()) blogPostService.deleteIndex(blogPost.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping({"/blogPost"})
    public ResponseEntity<Map<String, Object>> updateBlogPost(@RequestBody BlogPost blogPost) {
        blogPostService.saveIndex(blogPost);
        return new ResponseEntity<Map<String, Object>>(
                Map.of(
                        "blogPost", blogPost,
                        "saved", true
                ),
                HttpStatus.OK
        );
    }

    @GetMapping({"/search/"})
    @ResponseBody
    public Page<BlogPost> searchBlogPosts(@RequestParam String searchString, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> max) {
        Integer maxResults = max.orElse(25);
        Integer pageNum = page.orElse(0);
        log.info("Searching by string {} (page {}, max {} results)", searchString, page, max);
        return blogPostService.searchBlogPosts(searchString, pageNum, maxResults);
    }

    @GetMapping({"/search/article"})
    @ResponseBody
    public Page<BlogPost> searcArticles(@RequestParam String article, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> max) {
        Integer maxResults = max.orElse(25);
        Integer pageNum = page.orElse(0);
        log.info("Searching by article {} (page {}, max {} results)", article, page, max);
        return blogPostService.searchByArticle(article, pageNum, maxResults);
    }
}

