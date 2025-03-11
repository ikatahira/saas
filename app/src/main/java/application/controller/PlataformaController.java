package application.controller;

import application.model.Plataforma;
import application.repository.PlataformaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/plataformas")
public class PlataformaController {

    @Autowired
    private PlataformaRepository plataformaRepository;

    @GetMapping("/list")
    public String listPlataformas(Model model) {
        List<Plataforma> plataformas = plataformaRepository.findAll();
        model.addAttribute("plataformas", plataformas);
        return "plataformas/list"; // Nome da view para listar as plataformas
    }

    @GetMapping("/add")
    public String addPlataformaForm(Model model) {
        model.addAttribute("plataforma", new Plataforma()); // Adiciona um objeto Plataforma vazio para o formulário
        return "plataformas/add"; // Nome da view para o formulário de adição
    }

    @PostMapping("/add")
    public String addPlataformaSubmit(@ModelAttribute Plataforma plataforma) {
        plataformaRepository.save(plataforma);
        return "redirect:/plataformas/list"; // Redireciona para a lista após adicionar
    }

    @GetMapping("/edit/{id}")
    public String editPlataformaForm(@PathVariable("id") long id, Model model) {
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de plataforma inválido:" + id));
        model.addAttribute("plataforma", plataforma);
        return "plataformas/edit"; // Nome da view para o formulário de edição
    }

    @PostMapping("/update/{id}")
    public String updatePlataforma(@PathVariable("id") long id, @ModelAttribute Plataforma plataforma) {
        plataforma.setId(id); // Garante que estamos atualizando a plataforma correta
        plataformaRepository.save(plataforma);
        return "redirect:/plataformas/list"; // Redireciona para a lista após atualizar
    }

    @GetMapping("/delete/{id}")
    public String deletePlataforma(@PathVariable("id") long id) {
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de plataforma inválido:" + id));
        plataformaRepository.delete(plataforma);
        return "redirect:/plataformas/list"; // Redireciona para a lista após excluir
    }
}