package eus.birt.dam.service.exceptions;

public class InvalidNotebookException extends RuntimeException {
    public InvalidNotebookException() {
        super("Selecciona un equipo que exista.");
    }
}
