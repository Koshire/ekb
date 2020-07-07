package com.ktaksv.ekb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService service;

    @Autowired
    public SecurityConfig(CustomUserDetailService service) {
        this.service = service;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                csrf().disable()
                .httpBasic()
                .and()
                .sessionManagement()
                .and()
                .authorizeRequests()
                .antMatchers("/world").hasAnyRole("USER")
                .antMatchers("/person").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()

                .and()
                .logout().deleteCookies("JSESSIONID")

                .and()
                .rememberMe().rememberMeParameter("remember-me");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication()
//                .passwordEncoder(this.getPasswordEncoder())
//                .withUser("ADMIN").password(this.passwordEncoder().encode("1234567")).roles("USER", "ADMIN")
//                .and()
//                .withUser("USER").password(this.passwordEncoder().encode("1234567")).roles("ADMIN");


        auth.userDetailsService(service).passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
