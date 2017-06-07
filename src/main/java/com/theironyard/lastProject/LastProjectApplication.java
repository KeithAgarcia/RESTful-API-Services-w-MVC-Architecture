package com.theironyard.lastProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		LastProjectApplication.class,
		Jsr310JpaConverters.class
})
public class LastProjectApplication extends WebSecurityConfigurerAdapter {
	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(LastProjectApplication.class, args);
	}

	@Bean
	public PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		return mapper;
	}


	@Bean
	public CorsConfigurationSource corsConfiguration() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Stream.of("*").collect(Collectors.toList()));
		configuration.setAllowedMethods(Stream.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH").collect(Collectors.toList()));

		configuration.setAllowCredentials(true);

		configuration.setAllowedHeaders(Stream.of("*").collect(Collectors.toList()));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}


	@Override
	public void configure(HttpSecurity http) throws Exception{
			http.cors().and()
				.exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
					if (exception != null) {
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}).and()
				.authorizeRequests()
				.antMatchers("/", "/home", "/new-user").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
					.successHandler((a, b, c) -> { })
					.loginPage("/login")
					.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.permitAll()
				.and()
				.csrf().disable();

//		http.authorizeRequests().antMatchers("/", "/**").permitAll().and().csrf().disable();
	}

	@Autowired
	public void configAuth(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
		auth
				.jdbcAuthentication()
				.passwordEncoder(encoder)
				.dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from users where username = ?")
				.authoritiesByUsernameQuery("select users.username, authorities.role_name from users inner join authorities on users.id = authorities.user_id where users.username = ?");
	}
}

