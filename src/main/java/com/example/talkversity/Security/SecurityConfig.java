package com.example.talkversity.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // to configure the authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    // to configure  the authorization
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/","/login","/*.css","/*.PNG","/*.png","/*.js","/signup","/admin","/home").permitAll()
//                .antMatchers("/", "/signup","/login","/*.css","/*.PNG","/H2-console/**", "/addDrug","/UserDrugs", "https://dailymed.nlm.nih.gov/dailymed/services/v2/*","/reviews").permitAll()
//                .antMatchers("/","/about", "/signup","/login","/*.css","/*.PNG","/*.png","/*.js","/*.svg","/*.ttf","/resources/**","/fonts/**","/css/**","/contactform/**","/img/**","/js/**").permitAll()
//                .antMatchers("/resources/**").permitAll()
//                .antMatchers("/*.css").permitAll()
//                .antMatchers("/*.js").permitAll()
//                .antMatchers("/*.PNG").permitAll()
//                .antMatchers("/*.jpg").permitAll()
//                .antMatchers("/resources/**").permitAll()
//
//                .antMatchers("/admin").hasAuthority("ADMIN")
//                .antMatchers("/courses").hasAnyAuthority("STUDENT","ADMIN","MAIN_ADMIN")
//                .anyRequest().authenticated()//any other pages you have to be authenticated
//                .and().formLogin().loginPage("/login")
//                .defaultSuccessUrl("/home", true)
//                .failureUrl("/login-error")
//                .permitAll()
//                .usernameParameter("username").passwordParameter("password")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .permitAll()
//                ;
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .antMatchers("/home", "/signup","/login","/*.css","/*.PNG").permitAll()
                .antMatchers("/","/about", "/signup","/login","/*.css","/*.PNG","/*.png","/*.js","/*.svg","/*.ttf","/resources/**","/fonts/**","/css/**","/img/**","/js/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/*.PNG").permitAll()
                .antMatchers("/*.jpg").permitAll()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/courses").hasAuthority("STUDENT")
                .anyRequest().authenticated()//any other pages you have to be authenticated
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
