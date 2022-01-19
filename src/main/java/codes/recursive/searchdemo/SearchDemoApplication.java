package codes.recursive.searchdemo;

import codes.recursive.searchdemo.domain.Favorite;
import codes.recursive.searchdemo.service.FavoriteService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SearchDemoApplication {

	private final ElasticsearchOperations searchOperations;
	private final FavoriteService favoriteService;

	public SearchDemoApplication(ElasticsearchOperations searchOperations, FavoriteService favoriteService) {
		this.searchOperations = searchOperations;
		this.favoriteService = favoriteService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SearchDemoApplication.class, args);
	}

	@PreDestroy
	public void deleteIndex() {
		log.info("Deleting index...");
		searchOperations.indexOps(Favorite.class).delete();
	}

	@PostConstruct
	public void buildIndex() {
		log.info("Building index...");
		searchOperations.indexOps(Favorite.class).refresh();
		favoriteService.deleteAll();

		List<Favorite> favorites = new ArrayList<>();
		for(int i=0; i<500; i++) {
			Faker faker = new Faker();
			Favorite favorite = Favorite.builder()
					.favoriteArtist(faker.artist().name())
					.favoriteBeer(faker.beer().name())
					.favoriteBook(faker.book().title())
					.favoriteColor(faker.color().name())
					.favoriteCat(faker.cat().breed())
					.favoriteSuperhero(faker.superhero().name())
					.build();
			favorites.add(favorite);
		}
		log.info("Creating bulk index of {} records", favorites.size());
		favoriteService.createProductIndexBulk(favorites);
	}
}
