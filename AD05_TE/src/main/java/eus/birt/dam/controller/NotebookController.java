package eus.birt.dam.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.ui.Model;

import eus.birt.dam.NotebookColorMap;
import eus.birt.dam.controller.form.NotebookForm;
import eus.birt.dam.domain.Notebook;
import eus.birt.dam.service.NotebookService;

@Controller
@RequestMapping("/notebooks")
public class NotebookController {
    private final NotebookService service;

    public NotebookController(NotebookService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("notebooks", service.listar());
        model.addAttribute("NotebookColorMap", NotebookColorMap.class);
        return "notebooks/list";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("notebookForm", new NotebookForm());
        model.addAttribute("NotebookColorMap", NotebookColorMap.class);
        return "notebooks/form";
    }

    @PostMapping("/new")
    public String crear(@Valid @ModelAttribute("notebookForm") NotebookForm form,
                        BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "notebooks/form";
        }
        try {
            service.crear(form.getName(), form.getDescription(),
                    form.getSubject(), form.getColor(), form.isFavorite());
        } catch (DataIntegrityViolationException ex) {
            result.reject("database", "no se pudo guardar. Revisa los datos.");
            return "notebooks/form";
        }
        redirect.addFlashAttribute("success", "Cuaderno creado correctamente.");
        return "redirect:/notebooks";
    }

    @GetMapping("/{id}/edit")
    public String editar(@PathVariable("id") Long id, Model model) {
        Notebook notebook = service.obtener(id);
        NotebookForm form = new NotebookForm();
        form.setName(notebook.getName());
        form.setDescription(notebook.getDescription());
        form.setSubject(notebook.getSubject());
        form.setColor(notebook.getColor());
        form.setFavorite(notebook.isFavorite());
        
        model.addAttribute("notebookForm", form);
        model.addAttribute("editId", id);
        model.addAttribute("NotebookColorMap", NotebookColorMap.class);
        return "notebooks/form";
    }

    @PostMapping("/{id}/edit")
    public String actualizar(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("notebookForm") NotebookForm form,
                             BindingResult result, Model model,
                             RedirectAttributes redirect) {
        model.addAttribute("editId", id);
        if (result.hasErrors()) {
            return "notebooks/form";
        }
        try {
            service.actualizar(id, form.getName(), form.getDescription(),
                    form.getSubject(), form.getColor(), form.isFavorite());
        } catch (DataIntegrityViolationException ex) {
            result.reject("database", "no se pudo actualizar. Revisa los datos.");
            return "notebooks/form";
        }
        redirect.addFlashAttribute("success", "Cuaderno actualizado correctamente.");
        return "redirect:/notebooks";
    }

    @GetMapping("/{id}/delete")
    public String confirmarBorrado(@PathVariable("id") Long id, Model model) {
        model.addAttribute("notebook", service.obtener(id));
        model.addAttribute("count", service.contarNotas(id));
        return "notebooks/delete"; // Solo muestra la confirmacion, no borra.
    }

    @PostMapping("/{id}/delete")
    public String borrar(@PathVariable("id") Long id, RedirectAttributes redirect) {
        service.eliminar(id);
        redirect.addFlashAttribute("success", "Cuaderno y sus notas eliminados.");
        return "redirect:/notebooks";
    }
}

