package com.linkar.project.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.linkar.project.Repository.TarefaRepository;
import com.linkar.project.inum.Prioridade;
import com.linkar.project.model.Tarefa;
import com.linkar.project.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class AgendaController {

    @Autowired
    private TarefaRepository tarefaRepo;

    // ---------------------------
    // Página da agenda
    // ---------------------------
    @GetMapping("/agenda")
    public String agenda(
            @RequestParam(required = false) String data,
            Model model,
            HttpSession session) {

        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        if (u == null) return "redirect:/login";

        LocalDate dia = (data == null ? LocalDate.now() : LocalDate.parse(data));

        List<Tarefa> tarefas = tarefaRepo.findByIdUsuarioAndDataOrderByPrioridadeAsc(u.getId(), dia);

        model.addAttribute("tarefas", tarefas);
        model.addAttribute("diaSelecionado", dia);
        model.addAttribute("prioridades", Prioridade.values());

        return "Agenda";
    }

    // ---------------------------
    // Criar nova tarefa
    // ---------------------------
    @PostMapping("/agenda/nova")
    public String novaTarefa(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestParam Prioridade prioridade,
            HttpSession session) {

        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        if (u == null) return "redirect:/login";

        Tarefa t = new Tarefa();
        t.setTitulo(titulo);
        t.setDescricao(descricao);
        t.setPrioridade(prioridade);
        t.setData(LocalDate.parse(data));
        t.setIdUsuario(u.getId());

        tarefaRepo.save(t);

        return "redirect:/agenda";
    }

    // ---------------------------
    // Excluir tarefa
    // ---------------------------
    @GetMapping("/agenda/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        if (u == null) return "redirect:/login";

        Tarefa t = tarefaRepo.findById(id).orElse(null);
        if (t != null && t.getIdUsuario().equals(u.getId())) {
            tarefaRepo.delete(t);
        }

        return "redirect:/agenda";
    }
}
