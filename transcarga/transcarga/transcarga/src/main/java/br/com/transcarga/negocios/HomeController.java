package br.com.transcarga.negocios;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController - Controla a porta de entrada (Landing Page)
 * e o Dashboard principal do sistema.
 */
@Controller
public class HomeController {

    private final FreteService freteService;

    public HomeController(FreteService freteService) {
        this.freteService = freteService;
    }

    /**
     * Rota Raiz: Abre a Landing Page (Página Inicial com a imagem do caminhão).
     * Esta é a página que o usuário vê assim que acessa o endereço do site.
     */
    @GetMapping("/")
    public String landingPage() {
        return "paginicial";
    }

    /**
     * Rota de Início: Abre o Dashboard (index.html) com as estatísticas.
     * Corresponde ao link "Início" do menu e ao botão "Acesse o sistema".
     */
    @GetMapping("/inicio")
    public String index(Model model) {
        model.addAttribute("totalFretes", freteService.totalFretes());
        model.addAttribute("pesoTotal", freteService.pesoTotal());
        model.addAttribute("fretesPendentes", freteService.fretesPendentes());
        model.addAttribute("fretesEmTransito", freteService.fretesEmTransito());
        model.addAttribute("fretesEntregues", freteService.fretesEntregues());
        return "index";
    }
}