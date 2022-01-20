package codes.recursive.searchdemo;

import codes.recursive.searchdemo.domain.BlogPost;
import codes.recursive.searchdemo.service.BlogPostService;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SearchDemoApplication {

	private final ElasticsearchOperations searchOperations;
	private final BlogPostService blogPostService;

	public SearchDemoApplication(ElasticsearchOperations searchOperations, BlogPostService blogPostService) {
		this.searchOperations = searchOperations;
		this.blogPostService = blogPostService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SearchDemoApplication.class, args);
	}

	@PreDestroy
	public void deleteIndex() {
		log.info("Deleting index...");
		searchOperations.indexOps(BlogPost.class).delete();
	}

	@PostConstruct
	@SuppressWarnings({"unchecked"})
	public void buildIndex() throws IOException, FeedException {
		log.info("Building index...");
		searchOperations.indexOps(BlogPost.class).refresh();
		blogPostService.deleteAll();

		log.info("Importing blog posts...");
		URL feedSource = new URL("https://recursive.codes/blog/feed");
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(feedSource));

		List<BlogPost> blogPosts = new ArrayList<>();
		feed.getEntries().forEach( (s) -> {
			SyndEntry item = (SyndEntry) s;
			String article = ((SyndContentImpl) item.getContents().get(0)).getValue();
			article = StringEscapeUtils.unescapeHtml4(article);
			BlogPost blogPost = BlogPost.builder()
					.title(item.getTitle())
					.description(item.getDescription().getValue())
					.article(article)
					.build();
			blogPosts.add(blogPost);
		});
		log.info("Creating bulk index of {} records", blogPosts.size());
		blogPostService.createProductIndexBulk(blogPosts);
	}
}
