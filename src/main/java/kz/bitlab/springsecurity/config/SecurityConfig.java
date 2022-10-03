package kz.bitlab.springsecurity.config;

import kz.bitlab.springsecurity.dto.UserDto;
import kz.bitlab.springsecurity.services.UserService;
import kz.bitlab.springsecurity.services.iml.UserServiceImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true)
public class SecurityConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService()).passwordEncoder(passwordEncoder());

        http.exceptionHandling().accessDeniedPage("/403");

        http.authorizeRequests().antMatchers("/css/**", "/js/**").permitAll();

        http.formLogin()
                .loginProcessingUrl("/auth")
                .usernameParameter("user_email")
                .passwordParameter("user_password")
                .loginPage("/enter")
                .defaultSuccessUrl("/profile")
                .failureUrl("/enter?error");
        http.logout()
                .logoutSuccessUrl("/enter")
                .logoutUrl("/exit");

        return http.build();
    }

    @Bean
    @Scope("prototype")
    public UserDto userDto() {
        return UserDto.builder()
                .email("asd")
                .fullName("Dsa")
                .password("Asdasd")
                .build();
    }
}
