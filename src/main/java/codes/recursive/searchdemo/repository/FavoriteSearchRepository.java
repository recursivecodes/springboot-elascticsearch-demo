package codes.recursive.searchdemo.repository;

import codes.recursive.searchdemo.domain.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FavoriteSearchRepository extends ElasticsearchRepository<Favorite, String> {
    Page<Favorite> findByFavoriteBeer(String favoriteBeer, Pageable pageable);
    @Query("{ \"query_string\": { \"query\": \"?0\" }}")
    Page<Favorite> search(String searchString, Pageable pageable);
}

