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

