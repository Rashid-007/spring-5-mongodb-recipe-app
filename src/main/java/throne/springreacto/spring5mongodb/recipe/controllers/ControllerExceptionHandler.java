package throne.springreacto.spring5mongodb.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global Exception Handler
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception ex) {
        log.error("Handling invalid number format Exception");
        log.error(ex.getMessage());

        ModelAndView notFoundView = new ModelAndView();

        notFoundView.setViewName("400error");
        notFoundView.addObject("exception", ex);

        return notFoundView;
    }
}
