package eus.birt.dam.service;

import eus.birt.dam.domain.Notebook;
import eus.birt.dam.repository.NotebookRepository;
import eus.birt.dam.service.exceptions.NotFoundException;
import eus.birt.dam.repository.NoteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NotebookService {
    private final NotebookRepository notebooks;
    private final NoteRepository notes;

    public NotebookService(NotebookRepository notebooks, NoteRepository notes) {
        this.notebooks = notebooks;
        this.notes = notes;
    }

    public List<Notebook> listar() {
        return notebooks.findAll(
        		Sort.by(Direction.DESC, "favorite")
        		.and(Sort.by(Direction.ASC,"name")));
    }
    
    public List<Notebook> listarFecha() {
        return notebooks.findAll(
        		Sort.by(Direction.DESC, "created"));
    }

    public Notebook obtener(Long id) {
        return notebooks.findById(id)
                .orElseThrow(() -> new NotFoundException("El cuaderno no existe."));
    }

    public long contarNotas(Long id) {
        return notes.countByNotebook_Id(id);
    }

    @Transactional
    public void crear(String name, String description, String subject, int color, boolean favorite) {
        notebooks.save(new Notebook(name, description, subject, color, favorite, LocalDateTime.now()));
    }

    @Transactional
    public void actualizar(Long id, String name, String description, String subject, int color, boolean favorite) {
        Notebook notebook = obtener(id);
        notebook.actualizar(name, description, subject, color, favorite);
        // Entidad gestionada: Hibernate detecta los cambios al finalizar.
    }

    @Transactional
    public void eliminar(Long id) {
        Notebook notebook = obtener(id);
        notebooks.delete(notebook); // Activa cascade REMOVE hacia las notas.
    }
}
