package br.com.meli.desafio_final.dto;

public class TokenDto {

    private String token;
    private String type;

    public TokenDto(String token, String tipo) {
        this.token = token;
        this.type = tipo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setTipo(String type) {
        this.type = type;
    }

}