package eus.birt.dam.controller.form;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class NoteForm {
    @NotBlank(message = "Escribe el titulo.")
    @Size(max = 100, message = "El titulo admite hasta 100 caracteres.")
    private String title;
    @NotBlank(message = "Escribe tu nota.")
    @Size(max = 1200, message = "La nota admite hasta 1200 caracteres.")
    private String content;

    @NotNull(message = "Selecciona un cuadermp.")
    @Positive(message = "El identificador debe ser positivo.")
    private Long notebookId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getNotebookId() { return notebookId; }
    public void setNotebookId(Long notebookId) { this.notebookId = notebookId; }
}
