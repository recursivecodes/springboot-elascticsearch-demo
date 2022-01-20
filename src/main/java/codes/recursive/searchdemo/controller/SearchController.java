package codes.recursive.searchdemo.controller;

import codes.recursive.searchdemo.domain.BlogPost;
import codes.recursive.searchdemo.service.BlogPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"/search/{searchString}", "/search/{searchString}/{page}/{max}"})
    @ResponseBody
    public Page<BlogPost> searchBlogPosts(@PathVariable String searchString, @PathVariable Optional<Integer> page, @PathVariable Optional<Integer> max) {
        Integer maxResults = max.orElse(25);
        Integer pageNum = page.orElse(0);
        log.info("Searching by string {} (page {}, max {} results)", searchString, page, max);
        return blogPostService.searchBlogPosts(searchString, pageNum, maxResults);
    }

    @GetMapping({"/search/article/{article}", "/search/article/{article}/{page}/{max}"})
    @ResponseBody
    public Page<BlogPost> searcArticles(@PathVariable String article, @PathVariable Optional<Integer> page, @PathVariable Optional<Integer> max) {
        Integer maxResults = max.orElse(25);
        Integer pageNum = page.orElse(0);
        log.info("Searching by article {} (page {}, max {} results)", article, page, max);
        return blogPostService.searchByArticle(article, pageNum, maxResults);
    }
}

