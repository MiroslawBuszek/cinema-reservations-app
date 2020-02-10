package pl.connectis.cinemareservationsapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.connectis.cinemareservationsapp.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UserRepository userRepository;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UserRepository userRepository) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository))
                .authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user/registration/client").permitAll()
                .antMatchers(HttpMethod.POST, "/user/registration/employee").hasAnyRole("EMPLOYEE","ADMIN")
                .antMatchers("/user").hasRole("ADMIN")
                .antMatchers("/room/**").hasAnyRole("EMPLOYEE","ADMIN")
                .antMatchers(HttpMethod.GET, "/movie").hasAnyRole("CLIENT","EMPLOYEE","ADMIN")
                .antMatchers("/movie/**").hasAnyRole("EMPLOYEE","ADMIN")
                .antMatchers(HttpMethod.GET, "/session").hasAnyRole("CLIENT","EMPLOYEE","ADMIN")
                .antMatchers("/session/**").hasAnyRole("EMPLOYEE","ADMIN")            //Todo for further settings
                .antMatchers("/ticket/**").hasAnyRole("CLIENT","EMPLOYEE","ADMIN")    //Todo for further settings
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() throws Exception{
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
