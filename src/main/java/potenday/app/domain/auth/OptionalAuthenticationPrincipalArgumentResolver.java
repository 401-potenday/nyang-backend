package potenday.app.domain.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OptionalAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationTokenService authenticationTokenService;

    public OptionalAuthenticationPrincipalArgumentResolver(AuthenticationTokenService authenticationTokenService) {
        this.authenticationTokenService = authenticationTokenService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OptionalAuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = parseTokenFrom(request);
        if (token != null && !token.isBlank()) {
            try {
                return authenticationTokenService.findUserByToken(token);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public String parseTokenFrom(HttpServletRequest httpServletRequest) {
        final String authorization = httpServletRequest.getHeader("Authorization");
        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer")) {
            return null;
        }
        return authorization.substring(7);
    }
}