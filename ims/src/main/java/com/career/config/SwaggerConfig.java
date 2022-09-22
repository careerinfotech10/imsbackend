package com.career.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
					.groupName("public-api")
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.career"))
					.paths(PathSelectors.any()).build();
	}

	

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Career Infotech API")
				.description("Career IT API reference for developers")
				.termsOfServiceUrl("http://www.careerinfotech.in")
				.contact(new Contact("Career IT", "www.careerinfotech.in", "careerinfotech@gmail.com"))
				.license("Career IT License")
				.licenseUrl("tureraosantosh@gmail.com")
				.version("1.0")
				.build();
	}

}