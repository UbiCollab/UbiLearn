package no.ntnu.stud.ubilearn.ubilearn_server.helloworld;

import com.yammer.dropwizard.config.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger derp";
    
    @NotEmpty
    @JsonProperty
    private String test;

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }
    public String getTest(){
    	return test;
    }
    
}