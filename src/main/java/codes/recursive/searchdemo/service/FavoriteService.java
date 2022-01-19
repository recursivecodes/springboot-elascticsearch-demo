package codes.recursive.searchdemo.service;

import codes.recursive.searchdemo.domain.Favorite;
import codes.recursive.searchdemo.repository.FavoriteSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteSearchRepository favoriteSearchRepository;

    public FavoriteService(FavoriteSearchRepository favoriteRepository) {
        this.favoriteSearchRepository = favoriteRepository;
    }

    public void createProductIndexBulk(final List<Favorite> favorites) {
        favoriteSearchRepository.saveAll(favorites);
    }

    public void createProductIndex(final Favorite favorite) {
        favoriteSearchRepository.save(favorite);
    }

    public void deleteAll() {
        favoriteSearchRepository.deleteAll();
    }

    public Page<Favorite> searchFavorites(final String searchString, final Integer page, final Integer maxResults) {
        Pageable pageable = PageRequest.of(page, maxResults);
        return favoriteSearchRepository.search(searchString, pageable);
    }

    public Page<Favorite> searchByFavoriteBeer(final String favoriteBeer, final Integer page, final Integer maxResults) {
        Pageable pageable = PageRequest.of(page, maxResults);
        return favoriteSearchRepository.findByFavoriteBeer(favoriteBeer, pageable);
    }
}
