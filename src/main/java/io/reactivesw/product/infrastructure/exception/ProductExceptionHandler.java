package io.reactivesw.product.infrastructure.exception;

import io.reactivesw.exception.handler.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by umasuo on 17/3/2.
 */
@Component
public class ProductExceptionHandler extends ExceptionHandler implements HandlerExceptionResolver {

  private static Logger logger = LoggerFactory.getLogger(ProductExceptionHandler.class);

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                       Object handler, Exception ex) {
    setResponse(request, response, handler, ex);
    return new ModelAndView();
  }
}
