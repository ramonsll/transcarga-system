package br.com.transcarga;

import br.com.transcarga.model.Frete;
import br.com.transcarga.negocios.FreteService;
import br.com.transcarga.persistencia.FreteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransCargaApplicationTests {

    @Autowired
    private FreteService freteService;

    @Autowired
    private FreteRepository freteRepository;

    @Test
    void contextLoads() {
        assertNotNull(freteService);
        assertNotNull(freteRepository);
    }

    @Test
    void deveCadastrarEListarFrete() {
        long totalAntes = freteService.totalFretes();

        Frete frete = new Frete();
        frete.setDestino("Recife, PE");
        frete.setPeso(500.0);
        frete.setTransportadora("PernaCargo Ltda");
        frete.setStatus(Frete.StatusFrete.PENDENTE);

        Frete salvo = freteService.cadastrarFrete(frete);

        assertNotNull(salvo.getId());
        assertEquals("Recife, PE", salvo.getDestino());
        assertEquals(totalAntes + 1, freteService.totalFretes());
    }

    @Test
    void deveBuscarPorTermo() {
        List<Frete> resultado = freteService.buscarPorTermo("São Paulo");
        assertNotNull(resultado);
    }
}
