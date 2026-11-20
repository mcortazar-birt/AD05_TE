package eus.birt.dam.controller;

import eus.birt.dam.domain.Note;
import eus.birt.dam.service.NoteService;
import eus.birt.dam.service.NotebookService;
import eus.birt.dam.service.exceptions.*;
import eus.birt.dam.NotebookColorMap;
import eus.birt.dam.controller.form.NoteForm;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService service;
    private final NotebookService notebookService;

    public NoteController(NoteService service, NotebookService notebookService) {
        this.service = service;
        this.notebookService = notebookService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "q", defaultValue = "") String q, Model model) {
        model.addAttribute("notes", service.listar(q));
        model.addAttribute("q", q);
        return "notes/list";
    }

    @GetMapping("/notebook/{id}")
    public String listarNotebook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("notes", service.listarNotebook(id));
        model.addAttribute("notebook", notebookService.obtener(id));
        model.addAttribute("NotebookColorMap", NotebookColorMap.class);
        return "notes/listFromNotebook";
    }
    
    private String mostrarFormulario(Model model) {
        model.addAttribute("notebooks", notebookService.listar());
        return "notes/form";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("noteForm", new NoteForm());
        return mostrarFormulario(model);
    }
    
    @GetMapping("/new/{id}")
    public String nuevo(@PathVariable("id") Long id,Model model) {
    	NoteForm form = new NoteForm();
    	form.setNotebookId(id);
        model.addAttribute("noteForm", form);
        return mostrarFormulario(model);
    }

    @PostMapping("/new")
    public String crear(@Valid @ModelAttribute("noteForm") NoteForm form,
                        BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return mostrarFormulario(model);
        }
        try {
            service.crear(form.getTitle(), form.getContent(), form.getNotebookId());
        } catch (InvalidNotebookException ex) {
            result.rejectValue("notebookId", "notebook.invalid", ex.getMessage());
            return mostrarFormulario(model);
        } catch (DataIntegrityViolationException ex) {
            result.reject("database", "No se pudo guardar. Revisa los datos.");
            return mostrarFormulario(model);
        }
        redirect.addFlashAttribute("success", "Nota creada correctamente.");
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String editar(@PathVariable("id") Long id, Model model) {
        Note note = service.obtener(id);
        NoteForm form = new NoteForm();
        form.setTitle(note.getTitle());
        form.setContent(note.getContent());
        form.setNotebookId(note.getNotebook().getId());
        model.addAttribute("noteForm", form);
        model.addAttribute("editId", id);
        return mostrarFormulario(model);
    }

    @PostMapping("/{id}/edit")
    public String actualizar(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("noteForm") NoteForm form,
                             BindingResult result, Model model, RedirectAttributes redirect) {
        model.addAttribute("editId", id);
        if (result.hasErrors()) {
            return mostrarFormulario(model);
        }
        try {
            service.actualizar(id, form.getTitle(), form.getContent(), form.getNotebookId());
        } catch (InvalidNotebookException ex) {
            result.rejectValue("notebookId", "notebook.invalid", ex.getMessage());
            return mostrarFormulario(model);
        } catch (DataIntegrityViolationException ex) {
            result.reject("database", "No se pudo actualizar. Revisa los datos.");
            return mostrarFormulario(model);
        }
        redirect.addFlashAttribute("success", "Nota actualizada correctamente.");
        return "redirect:/notes";
    }

    @GetMapping("/{id}/delete")
    public String confirmarBorrado(@PathVariable("id") Long id, Model model) {
        model.addAttribute("note", service.obtener(id));
        return "notes/delete";
    }

    @PostMapping("/{id}/delete")
    public String borrar(@PathVariable("id") Long id, RedirectAttributes redirect) {
        service.eliminar(id);
        redirect.addFlashAttribute("success", "Nota eliminada. Su cuaderno se conserva.");
        return "redirect:/notes";
    }
}
