package potenday.app.global.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Component
public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

  @Override
  protected void beforeRequest(HttpServletRequest request, String message) {
    super.beforeRequest(request, message);
  }

  @Override
  protected void afterRequest(HttpServletRequest request, String message) {

  }
}
