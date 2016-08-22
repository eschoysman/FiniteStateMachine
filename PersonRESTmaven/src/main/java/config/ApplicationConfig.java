package config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("person")
public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		System.out.println("ApplicationConfig.ApplicationConfig()");
		packages("services");
	}
	
}
