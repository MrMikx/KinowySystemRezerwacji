package pl.pjwstk.projekt.frontend;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin")
                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/movies/edit/*").hasRole("admin")
                .antMatchers("/movies/add").hasRole("admin")
                .antMatchers("/movie/delete/*").hasRole("admin")
                .antMatchers("/movies/programme/addForm").hasRole("admin")
                .and()
                .formLogin()
                .defaultSuccessUrl("/movies")
                .and()
                .logout()
                .logoutSuccessUrl("/movies");
    }
}
