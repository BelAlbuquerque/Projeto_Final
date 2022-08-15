package br.com.meli.desafio_final.configJwt;

import br.com.meli.desafio_final.repository.UserRepository;
import br.com.meli.desafio_final.service.implementation.AutenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticationService autenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    //autenticacao
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    //autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user/registry").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/adsenses").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/adsenses/list").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/adsenses/warehouse/{adsenseId}").permitAll()
                .antMatchers(HttpMethod.GET, "/batch/{adsenseId}").hasAnyAuthority("AGENT", "SELLER")
                .antMatchers(HttpMethod.GET, "/batch/due-date").hasAnyAuthority("AGENT", "SELLER")
                .antMatchers(HttpMethod.GET, "/batch/due-date/list").hasAnyAuthority("AGENT", "SELLER")
                .antMatchers(HttpMethod.POST, "/api/v1/fresh-products/inboundorder/").hasAnyAuthority("AGENT")
                .antMatchers(HttpMethod.PUT, "/api/v1/fresh-products/inboundorder/").hasAnyAuthority("AGENT")
                .antMatchers(HttpMethod.GET, "/api/v2/fresh-products").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/fresh-products/list").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/fresh-products/sortlist").hasAnyAuthority("AGENT", "SELLER")
                .antMatchers(HttpMethod.POST, "/api/v2/fresh-products/orders").hasAnyAuthority("BUYER")
                .antMatchers(HttpMethod.PUT, "/api/v2/fresh-products/orders/").hasAnyAuthority("BUYER")
                .antMatchers(HttpMethod.GET, "/api/v2/fresh-products/orders/{purchaseOrderId}").permitAll()

                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticationFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
    }
    //autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(autenticationService).passwordEncoder(encoder);
    }
}