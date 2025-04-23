package de.whak.hfk.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtAuthenticationConverter jwtConverter;

    public KeycloakJwtAuthenticationConverter() {
        jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        return jwtConverter.convert(jwt);
    }

    public static class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        private static final String ROLE_PREFIX = "ROLE_";

        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> authorities = scopesConverter.convert(jwt);
            authorities.clear();
            authorities.addAll(extractResourceRoles(jwt));
            return authorities;
        }

        private Collection<SimpleGrantedAuthority> extractResourceRoles(Jwt jwt) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            JsonNode accountingRolesNode = OBJECT_MAPPER
                    .valueToTree(jwt.getClaims())
                    .path("resource_access")
                    .path("accounting")
                    .path("roles");

            if (!accountingRolesNode.isArray()) {
                return authorities;
            }

            accountingRolesNode.forEach(roleNode -> {
                if (roleNode.isTextual()) {
                    authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + roleNode.asText()));
                }
            });

            return authorities;
        }
    }
}
