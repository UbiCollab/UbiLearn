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

@Path("/pasient")
@Produces(MediaType.APPLICATION_JSON)
public class PasientResource {
//    private final String template;
//    private final String info;
    private final AtomicLong counter;

    public PasientResource() {
//        this.template = template;
//        this.info = info;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name, @QueryParam("test") Optional<String> test) {
    	return null;
    }
}