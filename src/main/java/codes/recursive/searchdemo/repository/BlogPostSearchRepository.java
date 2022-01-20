package codes.recursive.searchdemo.repository;

import codes.recursive.searchdemo.domain.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BlogPostSearchRepository extends ElasticsearchRepository<BlogPost, String> {
    Page<BlogPost> findByArticle(String article, Pageable pageable);
    @Query("{ \"query_string\": { \"query\": \"?0\" }}")
    Page<BlogPost> search(String searchString, Pageable pageable);
}

