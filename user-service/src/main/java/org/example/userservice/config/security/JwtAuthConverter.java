package org.example.userservice.config.security;

import lombok.RequiredArgsConstructor;
import org.example.userservice.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.userservice.config.security.SecurityConstants.RESOURCES_ACCESS;

@RequiredArgsConstructor
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserService userService;
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    private final JwtAuthConverterProperties properties;

    public AbstractAuthenticationToken convert(Jwt jwt) {
        System.out.println(jwt);
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
        User user = userService.extractUserInfo(jwt);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return authToken;
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim(RESOURCES_ACCESS);

        Map<String, Object> resource;
        Collection<String> resourceRoles;


        if (resourceAccess == null
                || (resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId())) == null
                || (resourceRoles = (Collection<String>) resource.get(SecurityConstants.ROLES)) == null) {
            System.out.println( "Resource access "+resourceAccess);
            System.out.println("Property .getResources id :"+resourceAccess.get(properties.getResourceId()));
            System.out.println("In if block");
            return Set.of();
        }
        System.out.println("Resource role "+resourceRoles);
        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
    }
}