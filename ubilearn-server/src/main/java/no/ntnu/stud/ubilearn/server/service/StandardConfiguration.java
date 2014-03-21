package no.ntnu.stud.ubilearn.server.service;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class StandardConfiguration extends Configuration {

	 @NotEmpty
	 @JsonProperty
    private String template;
 
    public String getTemplate() {
        return template;
    }
}
