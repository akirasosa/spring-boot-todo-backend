package myapps.jwtapp;

import com.auth0.jwt.JWTSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(StatelessLoginFilter.class);

    UserDetailsService userDetailsService;

    public StatelessLoginFilter(String defaultFilterProcessesUrl, UserDetailsService userDetailsService,  AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        this.userDetailsService = userDetailsService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);

        // TODO extract this logic to service
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", authResult.getName());
        payload.put("expireAt", Instant.now().plusSeconds(10 * 24 * 60 * 60));

        JWTSigner jwtSigner = new JWTSigner("test-secret");
        String jwtToken = jwtSigner.sign(payload);

        response.setHeader("X-Auth-Token", jwtToken);
    }
}
