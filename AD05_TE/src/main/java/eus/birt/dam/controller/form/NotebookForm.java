package eus.birt.dam.controller.form;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class NotebookForm {
    @NotBlank(message = "Escribe el nombre.")
    @Size(max = 100, message = "El nombre admite hasta 80 caracteres.")
    private String name;
    @NotBlank(message = "Escribe la descripción.")
    @Size(max = 200, message = "La descripción admite hasta 200 caracteres.")
    private String description;
    @Size(max = 60, message = "El tema admite hasta 60 caracteres.")
    private String subject;
    
    @Digits(integer = 5, message = "El color ha de estar entre 0 y 99999", fraction = 0)
    @NotNull(message = "Selecciona un color.")
    @Positive(message = "El identificador debe ser positivo.")
    private Integer color;
    private boolean favorite;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; } 
    public Integer getColor() { return color; }
    public void setColor(Integer color) { this.color = color; }
    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}
