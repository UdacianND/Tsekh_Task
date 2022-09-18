package com.example.tsekh_task.security;


import com.example.tsekh_task.jwt.JwtTokenVerifier;
import com.example.tsekh_task.user_detail_service.UserDetailServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImp userDetailsService;
    private final JwtTokenVerifier jwtTokenVerifier;

    private final String[] publicUrls = {"/api/auth/login", "/api/auth/register","/v2/api-docs",
                    "/configuration/ui", "/swagger-resources/**", "/configuration/security",
                    "/swagger-ui.html", "/webjars/**"};
    private final String[] authorizedUrls = {"api/washer/**","**/add/**","**/update/**","**/avatar/**",
                    "api/washCompany/getId","**/analytics"};



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(publicUrls)
                .permitAll()
                .antMatchers(authorizedUrls)
                .hasAnyRole("ADMIN","OWNER")
                .antMatchers("/api/user-management/**","api/admin/**","api/journal/**")
                .hasAnyRole("OWNER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .usernameParameter("email");
        http
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http.cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenVerifier, UsernamePasswordAuthenticationFilter.class);

    }





    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
