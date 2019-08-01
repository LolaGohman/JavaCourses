package org.courses.lesson11.advice;

import org.courses.lesson11.exception.NoRightsToChangeDatabaseException;
import org.courses.lesson11.exception.TryToChangeDefaultUserException;
import org.courses.lesson11.exception.UnableToSaveUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = Logger.getLogger(ControllerExceptionHandler.class.getName());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({FileNotFoundException.class, RuntimeException.class})
    public ModelAndView exceptionHandler(Exception ex) {
        log.log(Level.SEVERE, ex.getMessage());
        return new ModelAndView("error").addObject("errorMessage",
                "Sorry, something went wrong!");
    }


    @ExceptionHandler({NoRightsToChangeDatabaseException.class})
    public ModelAndView exceptionHandlerForUserHasNoRightsToChangeDatabase() {
        return new ModelAndView("error").addObject("errorMessage",
                "You has no rights to change user list !");
    }

    @ExceptionHandler({UnableToSaveUserException.class})
    public ModelAndView exceptionHandlerForUnableToSaveUser() {
        return new ModelAndView("error").addObject("errorMessage",
                "User can not be saved! Maybe, it has incorrect fields " +
                        "or username already exists!");
    }

    @ExceptionHandler({TryToChangeDefaultUserException.class})
    public ModelAndView exceptionHandlerForDefaultUserChanging() {
        return new ModelAndView("error").addObject("errorMessage",
                "You're unable to change default user!");
    }
}

