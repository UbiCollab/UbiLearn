package no.ntnu.stud.ubilearn.server.helloworld;

import no.ntnu.stud.ubilearn.server.helloworld.Saying;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
	private String test;

    public HelloWorldResource(String template, String defaultName, String test) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        this.test = test;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(),
                          String.format(template, name.or(defaultName)), String.format(template, name.or(defaultName)));
    }
}