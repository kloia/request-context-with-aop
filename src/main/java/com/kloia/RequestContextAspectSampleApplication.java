package com.kloia;

import com.kloia.configuration.filter.RequestScopedAttributesFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;

@SpringBootApplication
public class RequestContextAspectSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestContextAspectSampleApplication.class, args);
	}

	@WebListener
	public class MyRequestContextListener extends RequestContextListener {

	}

}
