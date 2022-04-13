package codes.recursive.searchdemo.service;

import codes.recursive.searchdemo.domain.BlogPost;
import codes.recursive.searchdemo.repository.BlogPostSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    private final BlogPostSearchRepository blogPostSearchRepository;

    public BlogPostService(BlogPostSearchRepository blogPostRepository) {
        this.blogPostSearchRepository = blogPostRepository;
    }

    public Optional<BlogPost> findById(final String id) {
        return blogPostSearchRepository.findById(id);
    }

    public void saveIndexBulk(final List<BlogPost> blogPosts) {
        blogPostSearchRepository.saveAll(blogPosts);
    }

    public void saveIndex(final BlogPost blogPost) {
        blogPostSearchRepository.save(blogPost);
    }

    public void deleteIndex(final BlogPost blogPost) {
        blogPostSearchRepository.delete(blogPost);
    }

    public void deleteIndexAll() {
        blogPostSearchRepository.deleteAll();
    }

    public Page<BlogPost> searchBlogPosts(final String searchString, final Integer page, final Integer maxResults) {
        Pageable pageable = PageRequest.of(page, maxResults);
        return blogPostSearchRepository.search(searchString, pageable);
    }

    public Page<BlogPost> searchByArticle(final String article, final Integer page, final Integer maxResults) {
        Pageable pageable = PageRequest.of(page, maxResults);
        return blogPostSearchRepository.findByArticle(article, pageable);
    }
}
