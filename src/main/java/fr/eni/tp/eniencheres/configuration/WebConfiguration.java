package fr.eni.tp.eniencheres.configuration;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebSecurity
public class WebConfiguration implements WebMvcConfigurer {
	@Bean
	LocaleResolver localeResolver() {
		System.out.println("localeResolver");
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("fr"));
		return slr;
	}

	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	UserDetailsManager userDetailsManager(DataSource datasource) {
		var jdbcUserDetailsManager = new JdbcUserDetailsManager(datasource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("select pseudo, mot_de_passe, 1 from UTILISATEURS where pseudo=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select pseudo, 1 from UTILISATEURS where pseudo=?");
		
		return jdbcUserDetailsManager;
	}
	 
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http ) throws Exception
	{
		http.authorizeHttpRequests(auth->{
			auth.requestMatchers(HttpMethod.GET,"/formateurs").authenticated();// seulement les utilisateurs connectés
			auth.requestMatchers(HttpMethod.GET,"/formateurs/detail").hasRole("FORMATEUR");
			auth.requestMatchers(HttpMethod.GET,"/formateurs/creer").hasRole("ADMIN");
			auth.requestMatchers(HttpMethod.GET,"/contexte/utilisateur/all").permitAll();// seulement les utilisateurs connectés
			auth.requestMatchers("/css/*").permitAll();
			auth.requestMatchers("/images/*").permitAll();
			auth.requestMatchers("/*").permitAll();// C'est la fête, tout le monde à le droit
			
			auth.anyRequest().denyAll();// Seulement les utilisateurs non connectés ont accès
		}).csrf(AbstractHttpConfigurer::disable);
		
		//http.formLogin(Customizer.withDefaults());
	    http
        .formLogin(form -> form
                .loginPage("/login")
                .failureUrl("/login?loginError=true"))
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logoutSuccess=true")
                .deleteCookies("JSESSIONID"));

		return http.build();
	}

}
