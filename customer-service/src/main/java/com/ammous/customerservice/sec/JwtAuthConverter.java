package com.ammous.customerservice.sec;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rami AMMOUS
 */
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities,jwt.getClaim("preferred_username"));
    }

    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String , Object> realmAccess;
        Collection<String> roles;
        if(jwt.getClaim("realm_access")==null){
            return Set.of();
        }
        realmAccess = jwt.getClaim("realm_access");
        roles = (Collection<String>) realmAccess.get("roles");
        return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
    }

/**
 * {
 *   "exp": 1733312330,
 *   "iat": 1733312030,
 *   "jti": "62088780-b390-46df-ab99-797322d217fa",
 *   "iss": "http://localhost:8080/realms/casierjud",
 *   "aud": "account",
 *   "sub": "16f4e3b7-e2e8-4158-b8ff-5ed4855b22b8",
 *   "typ": "Bearer",
 *   "azp": "casierjud",
 *   "session_state": "1d29f84a-6174-4271-ab9a-a05e90f6a2bf",
 *   "acr": "1",
 *   "allowed-origins": [
 *     "/*"
 *   ],
 *   "realm_access": {
 *     "roles": [
 *       "offline_access",
 *       "default-roles-casierjud",
 *       "uma_authorization",
 *       "USER"
 *     ]
 *   },
 *   "resource_access": {
 *     "account": {
 *       "roles": [
 *         "manage-account",
 *         "manage-account-links",
 *         "view-profile"
 *       ]
 *     }
 *   },
 *   "scope": "profile email",
 *   "sid": "1d29f84a-6174-4271-ab9a-a05e90f6a2bf",
 *   "email_verified": false,
 *   "name": "rami AMMOUS",
 *   "preferred_username": "user1",
 *   "given_name": "rami",
 *   "family_name": "AMMOUS",
 *   "email": "user1@gmail.com"
 * }
 */
}
