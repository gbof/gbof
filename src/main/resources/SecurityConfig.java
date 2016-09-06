import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	 http.authorizeRequests()
	    .antMatchers("/adminview", "/settings", "/settingsAdd", "/users", "/userAdded", "/edituser", "/userEdited", "/teamAdded", "/userRemoved", "/confirmedComm", "/teamRemoved").access("hasRole('ROLE_ADMIN')")
	    .and().formLogin()
		.loginPage("/login").failureUrl("/login?error")
		.usernameParameter("username")
		.passwordParameter("password")
	    .and().logout().logoutSuccessUrl("/login?logout")
	    .and()
		.exceptionHandling().accessDeniedPage("/403")
	}
}