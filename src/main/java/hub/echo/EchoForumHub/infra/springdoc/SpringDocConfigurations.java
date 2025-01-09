package hub.echo.EchoForumHub.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfigurations {

	@Bean
	OpenAPI customOpenAPi() {
		return new OpenAPI()
				.components(new Components()
				.addSecuritySchemes("bearer-key",
						new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")))
				.info(new Info()
						.title("Echo Forum Hub")
						.description("API REST do Echo Forum Hub, contendo até o momento as funcionalidades de cadastro de tópicos.")
						.contact(new Contact()
								.name("Lindauro Neto")
								.url("https://github.com/LindauroNeto"))
						);
	}
}
