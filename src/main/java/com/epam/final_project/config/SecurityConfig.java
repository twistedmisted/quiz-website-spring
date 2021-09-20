package com.epam.final_project.config;

import com.epam.final_project.handler.CustomSuccessHandler;
import com.epam.final_project.service.MySQLUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MySQLUserDetailsService mySQLUserDetailsService;

    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(MySQLUserDetailsService mySQLUserDetailsService,
                          CustomSuccessHandler customSuccessHandler) {
        this.mySQLUserDetailsService = mySQLUserDetailsService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/test/**", "/registration", "/login/**").permitAll()
                    .antMatchers("/admin/**").hasAuthority("admin")
                    .antMatchers("/app/**").hasAnyAuthority("user", "admin")
                    .antMatchers("/banned/**").hasAnyAuthority("banned")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll().successHandler(customSuccessHandler)
                .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                    .csrf()
                    .disable()
                    .cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mySQLUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}