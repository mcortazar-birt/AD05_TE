package eus.birt.dam.controller;

import eus.birt.dam.service.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(annotations = Controller.class)
public class WebErrors {
    private static final Logger log = LoggerFactory.getLogger(WebErrors.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noEncontrado(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/message";
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String baseDeDatos(DataAccessException ex, Model model) {
        log.error("Fallo de acceso a datos", ex);
        model.addAttribute("message", "No se pudo completar la operacion de datos. Revisa la consola.");
        return "error/message";
    }
}