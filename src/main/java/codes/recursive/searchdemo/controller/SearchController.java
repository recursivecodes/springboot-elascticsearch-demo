package codes.recursive.searchdemo.controller;

import codes.recursive.searchdemo.domain.Favorite;
import codes.recursive.searchdemo.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
@Slf4j
public class SearchController {

    private FavoriteService favoriteService;

    @Autowired
    public SearchController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping({"/search/{searchString}", "/search/{searchString}/{page}/{max}"})
    @ResponseBody
    public Page<Favorite> searchFavorites(@PathVariable String searchString, @PathVariable Optional<Integer> page, @PathVariable Optional<Integer> max) {
        Integer maxResults = max.orElse(25);
        Integer pageNum = page.orElse(0);
        log.info("Searching by string {} (page {}, max {} results)", searchString, page, max);
        return favoriteService.searchFavorites(searchString, pageNum, maxResults);
    }

    @GetMapping({"/search/beer/{favoriteBeer}", "/search/beer/{favoriteBeer}/{page}/{max}"})
    @ResponseBody
    public Page<Favorite> searchBeers(@PathVariable String favoriteBeer, @PathVariable Optional<Integer> page, @PathVariable Optional<Integer> max) {
        Integer maxResults = max.orElse(25);
        Integer pageNum = page.orElse(0);
        log.info("Searching by favorite beer {} (page {}, max {} results)", favoriteBeer, page, max);
        return favoriteService.searchByFavoriteBeer(favoriteBeer, pageNum, maxResults);
    }
}

