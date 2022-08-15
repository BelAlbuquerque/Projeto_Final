package br.com.meli.desafio_final.configJwt;

import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticationFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UserRepository repository;

    public AutenticationFilter(TokenService tokenService, UserRepository repository){
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //obtem token do cabecalho da requisicao
        String token = getToken(request);

        //validar token
        if(tokenService.isValidToken(token)) {

            //autenticar o token
            makeAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }

    private void makeAuthentication(String token) {
        String userEmail = tokenService.getUserEmail(token);
        User user = this.repository.findByEmail(userEmail);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication); //forçando autenticacao pelo spring
        System.out.println("---->" + user.getAuthorities().toString());
    }

    private String getToken(HttpServletRequest request) {
        String token = "";
        String authorization = request.getHeader("Authorization");
        if(authorization==null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
            return null;
        }else {
            token = authorization.substring(7, authorization.length());
        }
        return token;
    }

}
