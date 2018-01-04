package com.jaime.auth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private final String token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbi5hZG1pbiIsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6IjM0M2EzMGQ5LTE1YWEtNDc0NS1iMWRkLTAwMDU1Zjc3MWQwMSIsImNsaWVudF9pZCI6ImluZmluaXRlIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.Qo3gOshm7vgkqExOuSM72i4CJJKPWoeZ9AC8kxelY7dfbPShuIFyx09KaFroySoxyZMEaKNVPO5n6iPdhTfTYIjgZJzQuv6z_Yz9JeI03G0ENP1ZJCnqKsiJ3BulhPHbMwRDFXkSlxgOOpCmPbBd6ft6E7uZ00iQ6bQmosI6xAUl-VvYi941hfBl1cTgxosTMp7TK3-90MRBm_sWccEMK8_u8DrT0C8-n9CM-cbqJHsAdij7L2Af-ItB664Tr-ZqO9Be6Xpw0UdITEynbVSWDy2LN99NPoEBK-qYnUvCi5BVwsl7AdfS0XAFwjxZTPeaBCSY27vWBzEjeXtXr59shA";
	@Bean
	public Docket api() {
		
		List<ResponseMessage> list = new java.util.ArrayList<>();
        list.add(new ResponseMessageBuilder().code(500).message("500 message")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
                .responseModel(new ModelRef("Result")).build());
		
		return new Docket(DocumentationType.SWAGGER_2).select()
														.apis(RequestHandlerSelectors.basePackage("com.jaime.auth"))
														.paths(PathSelectors.ant("/**"))
														.build()
														.useDefaultResponseMessages(false)
														.globalResponseMessage(RequestMethod.GET, list)
														.globalResponseMessage(RequestMethod.POST, list)
														//.securitySchemes(Collections.singletonList(apiKey()))
														//.securitySchemes(Collections.singletonList(securitySchema()))
														//.securityContexts(Collections.singletonList(securityContext()))
														.apiInfo(apiInfo());
	}
	
	@Bean
	public SecurityConfiguration security() {
	    return new SecurityConfiguration("jaimetest", // "client id",
	            "test", // "client secret",
	            null, // "realm",
	            null, // "app",
	            token, ApiKeyVehicle.HEADER, "Authorization", "," /* scope separator */);
	}
	
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("My API title").description("")
                .termsOfServiceUrl("https://www.example.com/api")
                .contact(new Contact("Hasson", "http://www.example.com", "hasson@example.com"))
                .license("Open Source").licenseUrl("https://www.example.com").version("1.0.0").build();
    }

	
}
