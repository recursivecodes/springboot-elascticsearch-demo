package codes.recursive.searchdemo.data.repository;

import codes.recursive.searchdemo.domain.BlogPost;
import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
}
