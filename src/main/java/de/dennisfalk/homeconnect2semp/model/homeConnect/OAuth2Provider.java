/*
 * MIT License
 *
 * Copyright (c) 2021 Dennis Nikolas Falk <mail@dennisfalk.de>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.dennisfalk.homeconnect2semp.model.homeConnect;


import com.google.gson.Gson;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * OAuth2 Provider Class
 * This class provides the methods to get an access token from the Home Connect API
 */
@Slf4j
@Entity
@Getter
@Setter
@ToString
public class OAuth2Provider{

    /**
     * Empty constructor
     */
    public OAuth2Provider(){    }

    /**
     * Constructor with parameters
     * @param name name of the entity
     */
    public OAuth2Provider(String name){
        this.name = name;
        this.responseType = "code";
        this.state = String.valueOf((int)(Math.random() * 10000000));
        this.grantType = "authorization_code";
        this.authorizationURL = "https://api.home-connect.com/security/oauth/authorize";
        this.tokenURL = "https://api.home-connect.com/security/oauth/token";
        this.redirectURI = "http://localhost:8080/oauth2/code";
        this.accessToken = "";
        this.clientId = null;
        this.clientSecret = null;
    }

    @Id
    @Column(length = 20, nullable = false, unique = true)
    String name;

    //#####Authorization Request
    //More Information: https://api-docs.home-connect.com/authorization#authorization-code-grant-flow

    /*
     URL for authorization
     https://api.home-connect.com/security/oauth/authorize
     */
    @Column
    String authorizationURL;

    /*
    Required.
    Client identifier which is generated after application registration.
    Length: 64 characters
     */
    @Column(length = 64)
    String clientId;

    /*
    Optional.
    Redirect URI which is defined in registered application.
    If this parameter isn't used then the first defined redirect URI is used.
     */
    @Column
    String redirectURI;

    /*
    Required.
    Value must be code.
     */
    @Column
    String responseType;

    /*
    Optional but recommended.
    Space separated (escaped by %20) list of permissions.
    Possible permissions are IdentifyAppliance (always required), Monitor, Control (see Authorization Scopes).
     */
    @Column
    String scope;

    /*
    Optional but recommended.
    This parameter shall be used to prevent cross-site request forgery.
    Use random number or hash of the session cookie and verify in the final redirect that the included value is the
    same as the initial value.
     */
    @Column
    String state;


    //#####Authorization Response

    /*
    Required.
    Code to be used in a subsequent access token request (see also Section 4.1.2 of [RFC6749]).
    It expires after 10 minutes.
     */
    @Column
    String code;

    /*
    Optional.
    Type of the returned code, currently always authorization_code.
     */
    @Column
    String grantType;

    //##### Access Token Request

    /*
     URL for access token request
     https://api.home-connect.com/security/oauth/token
     */
    @Column
    String tokenURL;


    /*
    Required.
    Secret of the client that requested the code. It is generated after application registration.
    Please note that this parameter is required for this flow.
    Length: 64 characters
     */
    @Column(length = 64)
    String clientSecret;


    //##### Access Token Response

    /*
    Required.
    Same as access token.
     */
    @Column(length = 4096)
    String idToken;

    /*
    Required.
    Token to be used in subsequent Home Connect API requests.
    Max length: 4096, normally: ~ 1000 characters depending on the used scope
     */
    @Column(length = 4096)
    String accessToken;

    /*
    Required.
    Expiration time of the access token and id_token in seconds, default: 86400.
     */
    @Column
    String expiresIn;

    /*
    Calculated expiration date of access token
     */
    @Column
    String expireDate;

    /*
    Calculated expiration date of refresh token
     */
    @Column
    String expireDateRefreshToken;

    /*
    Required.
    Token to be used to refresh access token (expires if it wasn't used within 60 days).
    Max length: 256, normally: ~ 125 characters
     */
    @Column(length = 256)
    String refreshToken;

    /*
    Optional.
    Type of the token, currently always Bearer.
     */
    @Column
    String tokenType;

    /**
     * Check if a refresh token is available
     * @return boolean
     */
    public boolean isRefreshable(){

        return refreshToken != null;
    }

    /**
     * Check if the client can be authorized
     * @return boolean
     */
    public boolean isAuthorizable(){
        return this.clientId != null && this.clientSecret != null;
    }

    /**
     * Check if the access token is expired
     * @return boolean
     */
    public boolean isExpired(){

        if(expireDate != null) {
            LocalDateTime dateNow = LocalDateTime.now();
            LocalDateTime tokenDate = LocalDateTime.parse(expireDate);

            return dateNow.compareTo(tokenDate) > 0;
        } else {
            return true;
        }
    }

    /**
     * check if the refresh token is expired
     * @return boolean
     */
    public boolean isRefreshTokenExpired(){
        if(expireDateRefreshToken != null) {
            LocalDateTime dateNow = LocalDateTime.now();
            LocalDateTime tokenDate = LocalDateTime.parse(expireDateRefreshToken);

            return dateNow.compareTo(tokenDate) > 0;
        } else {
            return true;
        }
    }

    /**
     * Calculates the expire date for the access token
     * @param expiresIn String with date
     */
    public void setExpiresIn(String expiresIn) {
        //Access Token calculating expire date
        this.expiresIn = expiresIn;
        LocalDateTime date = LocalDateTime.now();
        date = date.plusSeconds(Long.parseLong(expiresIn));
        this.setExpireDate(date.toString());

        //Refresh Token calculating expire date
        LocalDateTime date2 = LocalDateTime.now();
        date2 = date2.plusDays(60);
        this.setExpireDateRefreshToken(date2.toString());
    }

    /**
     * Readable expire date
     * @return String with readable date
     */
    public String getExpireDateReadable(){
        if(expireDate != null) {
            LocalDateTime expireDateReadable = LocalDateTime.parse(this.expireDate);
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            return expireDateReadable.format(myFormatObj);
        } else {
            return "kein Access Token vorhanden";
        }
    }

    /**
     * Method to obtain an access token
     * @return OAuth2Provider object
     * @throws IOException Throws exception
     */
    public OAuth2Provider obtainAccessToken() throws IOException {

        String tokenRequest =
                "client_id=" +
                        this.getClientId() +
                        "&client_secret=" +
                        this.getClientSecret() +
                        "&grant_type=authorization_code" +
                        "&code=" +
                        this.getCode();

        //Token Request
        URL url = new URL(this.getTokenURL());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Accept-Language", "de-DE");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = tokenRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Gson gson = new Gson();
            OAuth2ProviderTokenResponse oauth2Response = gson.fromJson(response.toString(), OAuth2ProviderTokenResponse.class);

            this.setIdToken(oauth2Response.getId_token());
            this.setAccessToken(oauth2Response.getAccess_token());
            this.setExpiresIn(oauth2Response.getExpires_in());
            this.setScope(oauth2Response.getScope());
            this.setRefreshToken(oauth2Response.getRefresh_token());
            this.setTokenType(oauth2Response.getToken_type());

            return this;
        }
    }

    /**
     * Method for refreshing the access token
     * @return OAuth2Provider object
     * @throws IOException throws exception
     */
    public OAuth2Provider refreshToken() throws IOException {

       String tokenRequest =
                "grant_type=refresh_token" +
                        "&refresh_token=" +
                        this.getRefreshToken() +
                        "&client_secret=" +
                        this.getClientSecret();

        //Token Request
        URL url = new URL(this.getTokenURL());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Accept-Language", "de-DE");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = tokenRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Gson gson = new Gson();
            OAuth2ProviderTokenResponse oauth2Response = gson.fromJson(response.toString(), OAuth2ProviderTokenResponse.class);

            this.setIdToken(oauth2Response.getId_token());
            this.setAccessToken(oauth2Response.getAccess_token());
            this.setExpiresIn(oauth2Response.getExpires_in());
            this.setScope(oauth2Response.getScope());
            this.setRefreshToken(oauth2Response.getRefresh_token());
            this.setTokenType(oauth2Response.getToken_type());
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OAuth2Provider that = (OAuth2Provider) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return 157751838;
    }
}
