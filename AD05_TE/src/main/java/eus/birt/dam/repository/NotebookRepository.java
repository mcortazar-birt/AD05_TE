package eus.birt.dam.repository;

import eus.birt.dam.domain.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {
}
