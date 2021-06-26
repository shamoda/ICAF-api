package com.application.icafapi.security.config;

import com.application.icafapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity  //extends class webSecurityAdapter
public class ICAFSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    protected void configure(HttpSecurity http)throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/attendee")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/editProposal")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/download/{type}/{fileName}")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/getWorkshops/search")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/researcher/filter")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/registerConductor")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/registerWorkshop")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1//committee")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        //ant matching to be done
    }
}