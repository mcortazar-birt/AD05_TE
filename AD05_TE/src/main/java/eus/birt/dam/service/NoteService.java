package eus.birt.dam.service;

import eus.birt.dam.domain.Note;
import eus.birt.dam.domain.Notebook;
import eus.birt.dam.service.exceptions.InvalidNotebookException;
import eus.birt.dam.service.exceptions.NotFoundException;
import eus.birt.dam.repository.NoteRepository;
import eus.birt.dam.repository.NotebookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NoteService {
    private final NoteRepository notes;
    private final NotebookRepository notebooks;

    public NoteService(NoteRepository notes, NotebookRepository notebooks) {
        this.notes = notes;
        this.notebooks = notebooks;
    }

    public List<Note> listar(String titulo) {
    	if (titulo == null || titulo.isBlank()) {
            return notes.findAll();
        }
        return notes.findByTitleContainingIgnoreCase(
                titulo.trim(), Sort.by(Direction.ASC, "created"));

        //return notes.findAll(Sort.by(Direction.ASC, "created"));
    }

    public List<Note> listarNotebook(Long id) {
        return notes.findByNotebookId(id);

        //return notes.findAll(Sort.by(Direction.ASC, "created"));
    }
    
    public Note obtener(Long id) {
        return notes.findWithNotebookById(id)
                .orElseThrow(() -> new NotFoundException("La nota no existe."));
    }

    private Notebook buscarNotebook(Long notebookId) {
        if (notebookId == null) {
            throw new InvalidNotebookException();
        }
        return notebooks.findById(notebookId).orElseThrow(InvalidNotebookException::new);
    }

    @Transactional
    public void crear(String title, String content, Long NotebookId) {
        Notebook notebook = buscarNotebook(NotebookId);
        System.out.println(notebook == null);
        Note note = new Note(title, content, LocalDateTime.now(), notebook);
        notes.save(note);
    }

    @Transactional
    public void actualizar(Long id, String title, String content, Long notebookId) {
        Note note = obtener(id);
        Notebook notebook = buscarNotebook(notebookId);
        note.actualizar(title, content);
        note.cambiarNotebook(notebook);
    }

    @Transactional
    public void eliminar(Long id) {
        Note note = obtener(id);
        note.getNotebook().getNotes().remove(note);
        notes.delete(note); // No hay cascada desde Note hacia Notebook.
    }
}
