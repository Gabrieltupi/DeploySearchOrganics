package com.vemser.dbc.searchorganic.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/auth/login", "/auth/cadastrar", "/").permitAll()
                        .antMatchers("/carteira/**").hasAnyRole("ADMIN", "EMPRESA", "USUARIO")

                        .antMatchers(HttpMethod.PUT, "/cupom").hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.POST, "/cupom").hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.GET, "/cupom").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/cupom/").hasAnyRole("ADMIN","EMPRESA", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/cupom/empresa/").hasAnyRole("ADMIN","EMPRESA", "USUARIO")

                        .antMatchers(HttpMethod.DELETE,"/empresa").hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.GET, "/empresa").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/empresa/produtos").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.PUT,"/empresa/").hasAnyRole("ADMIN", "EMPRESA")


                        .antMatchers(HttpMethod.PUT,"/pedido").hasAnyRole("ADMIN", "USUARIO")//
                        .antMatchers("/pedido/entregue/").hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.DELETE,"/pedido").hasAnyRole("ADMIN","USUARIO", "EMPRESA")
                        .antMatchers(HttpMethod.POST, "/pedido").hasAnyRole("ADMIN, USUARIO")
                        .antMatchers(HttpMethod.GET,"/pedido").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/pedido/usuario/").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.POST, "/pedido/pagar/").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.POST, "/pedido/entregue/").hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.PUT, "/pedido/").hasAnyRole("ADMIN", "USUARIO","EMPRESA")
                        .antMatchers("/relatorio/**").hasRole("ADMIN")

                        .antMatchers("/usuario").hasAnyRole("ADMIN", "USUARIO")//
                        .antMatchers(HttpMethod.DELETE, "/usuario").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET,"/usuario").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/usuario/cpf/", "/usuario/email/", "/usuario/").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.PUT, "/usuario").hasAnyRole("ADMIN", "USUARIO")

                        .antMatchers(HttpMethod.POST,"/endereco").hasAnyRole("ADMIN", "EMPRESA", "USUARIO")
                        .antMatchers(HttpMethod.PUT, "/endereco").hasAnyRole("ADMIN", "EMPRESA", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/endereco").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/endereco/usuario/").hasAnyRole("ADMIN", "USUARIO")
                        .antMatchers(HttpMethod.GET, "/endereco/usuario/").hasAnyRole("ADMIN", "USUARIO")

                        .antMatchers(HttpMethod.POST,"/produto","/produto/imagem" ).hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.PUT,"/produto").hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.DELETE,"/produto").hasAnyRole("ADMIN", "EMPRESA")
                        .antMatchers(HttpMethod.GET, "/produto", "/produto/categoria/","/produto/empresa/").hasAnyRole("ADMIN", "USUARIO")

                        .anyRequest().authenticated()
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");

    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://search-organic.vercel.app/")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}