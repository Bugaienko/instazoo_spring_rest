package ua.bugaienko.instazoo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.bugaienko.instazoo.entity.User;
import ua.bugaienko.instazoo.service.CustomUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Sergii Bugaienko
 */

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {

                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception exception) {
            LOG.error("Could not set user authentication " + exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }


    private String getJWTFromRequest(HttpServletRequest request) {
        String bearToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(bearToken) && bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearToken.split(" ")[1];
        }
        return null;

    }
}
