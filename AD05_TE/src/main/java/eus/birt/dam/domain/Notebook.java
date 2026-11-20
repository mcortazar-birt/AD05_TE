package eus.birt.dam.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notebook")
public class Notebook {
    @Id
    @SequenceGenerator(name = "notebook_gen", sequenceName = "notebook_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notebook_gen")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 200)
    private String description;
    @Column(nullable = true, length = 60)
    private String subject;
    
    @Column(nullable = false, length = 5)
    private int color;
    @Column(nullable = false, length = 1)
    private int favorite;
    @Column(name = "created", nullable = false)
    private LocalDateTime dateCreated;

    // Regla de esta demostracion: eliminar un equipo elimina sus fichas de ciclistas.
    // No se usa orphanRemoval: cambiar de equipo no debe borrar al ciclista.
    @OneToMany(mappedBy = "notebook", cascade = CascadeType.REMOVE)
    private List<Note> notes = new ArrayList<>();

    protected Notebook() { }

    public Notebook(String name, String description, String subject, int color, boolean favorite, LocalDateTime date) {
        actualizar(name, description, subject, color, favorite, date);
    }

    public void actualizar(String name, String description, String subject, int color, boolean favorite) {
    	actualizar(name, description, subject, color, favorite, dateCreated);
    }
    
    protected void actualizar(String name, String description, String subject, int color, boolean favorite, LocalDateTime date) {
        this.name = name.trim();
        this.description = description.trim();
        this.subject = (subject != null)? subject.trim() : subject;
        this.color = (Math.abs(color) > 99999)? 0 : Math.abs(color);
        this.favorite = ((boolean)favorite)? 1 : 0;
        this.dateCreated = date;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSubject() { return subject; }
    public int getColor() { return color;}
    public boolean isFavorite() { return favorite != 0;}
    public LocalDateTime getDateCreated() {return dateCreated;}
    public List<Note> getNotes() { return notes; }
}
