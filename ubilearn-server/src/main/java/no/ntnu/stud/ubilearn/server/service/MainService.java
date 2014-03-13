package no.ntnu.stud.ubilearn.server.service;

import no.ntnu.stud.ubilearn.server.helloworld.HelloWorldService;
import no.ntnu.stud.ubilearn.server.models.Article;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class MainService extends Service<StandardConfiguration> {
	  public static void main(String[] args) throws Exception {
	        new MainService().run(args);
	  }

	@Override
	public void initialize(Bootstrap<StandardConfiguration> bootstrap) {
		bootstrap.setName("Main service");
		
	}

	@Override
	public void run(StandardConfiguration configuration, Environment environment) throws Exception {
		final String template = configuration.getTemplate();
		environment.addResource(new ArticleResource(template));
	}

}
