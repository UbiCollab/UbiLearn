package no.ntnu.stud.ubilearn.server.service;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import no.ntnu.stud.ubilearn.server.models.Article;
import no.ntnu.stud.ubilearn.server.models.Pasient;


@Path("/article")
@Produces(MediaType.APPLICATION_JSON)
public class ArticleResource {

	private final AtomicLong counter;
	private Article article;
	private String format;
	
	public ArticleResource(String format) {
		
		counter = new AtomicLong();
		this.article = new Article("testTitle", "hur dur, lots of text");
		this.format = format;
	}
	
    @GET
    @Timed
	public Article getArticle(@QueryParam("article") Optional<String> name){
		return new Article("test", "fdsfdssd");
		
	}
}
