package br.com.meli.desafio_final.configJwt;

import br.com.meli.desafio_final.dto.UserDetailsData;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.repository.UserRepository;
import br.com.meli.desafio_final.service.implementation.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Optional;

public class ValidationFilter extends BasicAuthenticationFilter {
    public static final String HEADER_ATRIBUTO = "Authorization";
    public static final String ATRIBUTO_PREFIXO = "Bearer ";

    private UserRepository userRepository;

    public ValidationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String atributo = request.getHeader(HEADER_ATRIBUTO);

        if (atributo == null) {
            chain.doFilter(request, response);
            return;
        }

        if (!atributo.startsWith(ATRIBUTO_PREFIXO)) {
            chain.doFilter(request, response);
            return;
        }

        String token = atributo.replace(ATRIBUTO_PREFIXO, "");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token, request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token, HttpServletRequest request) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(AuthenticationFilter.TOKEN_SENHA)).build().verify(token);
        String userMail = jwt.getSubject();
        String userId = jwt.getKeyId();


        if (userMail == null) {
            return null;
        }
        System.out.println(userMail);
        System.out.println(userId);
        User user = userRepository.findByEmail(userMail);

        UserDetails userDetails = new UserDetailsData(Optional.of(user));
        System.out.println("userDetails: " + userDetails.getAuthorities());
        request.setAttribute("userId" , userId);


        return new UsernamePasswordAuthenticationToken(userMail,null, userDetails.getAuthorities());
    }

}
