package eus.birt.dam.repository;

import eus.birt.dam.domain.Note;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("select c from Note c join fetch c.notebook")
    List<Note> findAllWithNotebook();

    @Query("select c from Note c join fetch c.notebook where c.id = :id order by c.created asc")
    Optional<Note> findWithNotebookById(@Param("id") Long id);

 // Ampliacion opcional del listado: filtrar por apellido.
    @EntityGraph(attributePaths = "notebook")
    @Query("select n from Note n join fetch n.notebook where lower(n.title) like lower(concat('%', :titulo, '%')) order by n.created asc")
    List<Note> findByTitleContainingIgnoreCase(String titulo, Sort sort);
    
    @EntityGraph(attributePaths = "notebook")
    @Query("select n from Note n join fetch n.notebook where n.notebook.id = :id order by n.created asc")
    List<Note> findByNotebookId(@Param("id") Long id);
    
    long countByNotebook_Id(Long notebookId);
}
