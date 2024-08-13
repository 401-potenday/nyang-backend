package potenday.app.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String activeProfile = System.getProperty("spring.profiles.active");
        if ("local".equals(activeProfile) || "dev".equals(activeProfile)) {
            logRequest(request);
            return true;
        }

        return true;
    }

    private void logRequest(HttpServletRequest request) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Request ")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURI());

        logMessage.append(", Headers {");
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName ->
                        logMessage.append(headerName)
                                .append(": ")
                                .append(request.getHeader(headerName))
                                .append(", "));
        logMessage.append("}");

        logger.info(logMessage.toString());
    }
}