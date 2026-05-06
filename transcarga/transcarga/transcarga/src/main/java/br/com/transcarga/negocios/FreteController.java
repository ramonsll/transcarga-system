package br.com.transcarga.negocios;

import br.com.transcarga.model.Frete;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * FreteController - Camada de Negócios / Controle (MVC Controller)
 * Substitui o FreteServlet do tutorial original.
 * Recebe requisições HTTP, chama o Service e retorna as views Thymeleaf.
 */
@Controller
@RequestMapping("/fretes")
public class FreteController {

    private final FreteService freteService;

    public FreteController(FreteService freteService) {
        this.freteService = freteService;
    }

    // -------------------------------------------------------
    // GET /fretes/cadastrar → exibe formulário
    // (equivale ao doGet do FreteServlet quando action=cadastrar)
    // -------------------------------------------------------
    @GetMapping("/cadastrar")
    public String exibirFormCadastro(Model model) {
        model.addAttribute("frete", new Frete());
        model.addAttribute("statusList", Frete.StatusFrete.values());
        return "cadastrarFrete";
    }

    // -------------------------------------------------------
    // POST /fretes/cadastrar → salva novo frete
    // (equivale ao doPost do FreteServlet)
    // -------------------------------------------------------
    @PostMapping("/cadastrar")
    public String cadastrarFrete(@Valid @ModelAttribute("frete") Frete frete,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statusList", Frete.StatusFrete.values());
            return "cadastrarFrete";
        }

        freteService.cadastrarFrete(frete);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Frete para " + frete.getDestino() + " cadastrado com sucesso!");
        return "redirect:/fretes/listar";
    }

    // -------------------------------------------------------
    // GET /fretes/listar → lista todos os fretes
    // (equivale ao doGet do FreteServlet que montava a tabela HTML)
    // -------------------------------------------------------
    @GetMapping("/listar")
    public String listarFretes(@RequestParam(required = false) String busca,
                               @RequestParam(required = false) String status,
                               Model model) {

        List<Frete> fretes;

        if (status != null && !status.isBlank()) {
            try {
                Frete.StatusFrete statusEnum = Frete.StatusFrete.valueOf(status);
                fretes = freteService.buscarPorStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                fretes = freteService.listarTodos();
            }
        } else if (busca != null && !busca.isBlank()) {
            fretes = freteService.buscarPorTermo(busca);
        } else {
            fretes = freteService.listarTodos();
        }

        model.addAttribute("fretes", fretes);
        model.addAttribute("busca", busca);
        model.addAttribute("statusSelecionado", status);
        model.addAttribute("statusList", Frete.StatusFrete.values());
        model.addAttribute("totalEncontrado", fretes.size());
        return "listarFretes";
    }

    // -------------------------------------------------------
    // GET /fretes/editar/{id} → exibe formulário de edição
    // -------------------------------------------------------
    @GetMapping("/editar/{id}")
    public String exibirFormEdicao(@PathVariable Long id, Model model,
                                   RedirectAttributes redirectAttributes) {
        Optional<Frete> frete = freteService.buscarPorId(id);
        if (frete.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro",
                    "Frete com ID " + id + " não encontrado.");
            return "redirect:/fretes/listar";
        }
        model.addAttribute("frete", frete.get());
        model.addAttribute("statusList", Frete.StatusFrete.values());
        return "editarFrete";
    }

    // -------------------------------------------------------
    // POST /fretes/editar/{id} → atualiza frete
    // -------------------------------------------------------
    @PostMapping("/editar/{id}")
    public String atualizarFrete(@PathVariable Long id,
                                 @Valid @ModelAttribute("frete") Frete frete,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statusList", Frete.StatusFrete.values());
            return "editarFrete";
        }

        frete.setId(id);
        freteService.atualizarFrete(frete);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Frete atualizado com sucesso!");
        return "redirect:/fretes/listar";
    }

    // -------------------------------------------------------
    // POST /fretes/excluir/{id} → exclui frete
    // -------------------------------------------------------
    @PostMapping("/excluir/{id}")
    public String excluirFrete(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        freteService.excluirFrete(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Frete excluído com sucesso!");
        return "redirect:/fretes/listar";
    }
}
