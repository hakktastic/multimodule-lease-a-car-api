package nl.svb.leaseacarapi.leasecalculationservice.filter;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * Zuul custom logging filter.
 *
 */
@Component
public class ZuulLoggingFilter extends ZuulFilter {

  // TODO implement other filters for authorization and security

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * Priority.
   */
  @Override
  public int filterOrder() {
    return 1;
  }

  /**
   * When this filter should be executed, e.g.: {@code pre|post|error}.
   */
  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public Object run() throws ZuulException {

    final HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

    logger.info("Request URL -> {}", request.getRequestURL());
    logger.info("Request URI -> {}", request.getRequestURI());
    logger.info("Request     -> {}", request);

    return null;
  }

  /**
   * Whether this filter should be executed or not (depending on business logic).
   */
  @Override
  public boolean shouldFilter() {
    return true;
  }

}
