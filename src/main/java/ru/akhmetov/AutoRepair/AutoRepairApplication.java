package ru.akhmetov.AutoRepair;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
public class AutoRepairApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoRepairApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
				/*.setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldMatchingEnabled(true)
				.setSkipNullEnabled(Boolean.TRUE)
				.setFieldAccessLevel(PRIVATE);*/
		return mapper;
	}
	/*@Configuration
	public static class MvcConfig implements WebMvcConfigurer {
		@Value("${upload.path}")
		private String uploadPath;
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/static/img/**")
					.addResourceLocations("file:///C:/Data/img/");
			//.addResourceLocations("file://" + uploadPath + "/");
		}
	}*/
}
