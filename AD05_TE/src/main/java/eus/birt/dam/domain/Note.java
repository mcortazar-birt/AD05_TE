package eus.birt.dam.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "note")
public class Note {
    @Id
    @SequenceGenerator(name = "note_gen", sequenceName = "note_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_gen")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Column(name = "content", nullable = false, length = 1200)
    private String content;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notebook_id", nullable = false)
    private Notebook notebook;

    protected Note() { }

    public Note(String title, String content, LocalDateTime created, Notebook notebook) {
        actualizar(title, content, created);
        cambiarNotebook(notebook);
    }

    public void actualizar(String title, String content) {
    	actualizar(title, content, created);
    }
    
    protected void actualizar(String title, String content, LocalDateTime created) {
        this.title = title.trim();
        this.content = content.trim();
        this.created = Objects.requireNonNull(created);
    }

    // Ambos equipos y el ciclista deben gestionarse dentro de la misma transaccion.
    public void cambiarNotebook(Notebook nuevo) {
        System.out.println((nuevo == null) + nuevo.getName());

        Objects.requireNonNull(nuevo, "El cuaderno es obligatorio");
        if (notebook == nuevo) {
            return;
        }
        if (notebook != null) {
            notebook.getNotes().remove(this);
        }
        notebook = nuevo; // Lado propietario: escribe Note.Notebook_id.
        if (!nuevo.getNotes().contains(this)) {
            nuevo.getNotes().add(this);
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreated() { return created; }
    public Notebook getNotebook() { return notebook; }
}
