package kea.alog.issue;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssueApplication {

	public static void main(String[] args) {
		//SpringApplication.run(IssueApplication.class, args);
		SpringApplication app = new SpringApplication(IssueApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
		app.run(args);
	}

}
