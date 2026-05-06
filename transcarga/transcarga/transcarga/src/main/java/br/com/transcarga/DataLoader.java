package br.com.transcarga;

import br.com.transcarga.model.Frete;
import br.com.transcarga.persistencia.FreteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataLoader - Insere dados de exemplo na inicialização da aplicação.
 * Útil para testar o sistema sem precisar cadastrar manualmente.
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner carregarDados(FreteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Frete f1 = new Frete();
                f1.setDestino("São Paulo, SP");
                f1.setPeso(1200.0);
                f1.setTransportadora("Rápido Frete Ltda");
                f1.setStatus(Frete.StatusFrete.ENTREGUE);

                Frete f2 = new Frete();
                f2.setDestino("Belo Horizonte, MG");
                f2.setPeso(450.5);
                f2.setTransportadora("TransNorte S.A.");
                f2.setStatus(Frete.StatusFrete.EM_TRANSITO);

                Frete f3 = new Frete();
                f3.setDestino("Fortaleza, CE");
                f3.setPeso(800.0);
                f3.setTransportadora("Nordeste Express");
                f3.setStatus(Frete.StatusFrete.PENDENTE);

                Frete f4 = new Frete();
                f4.setDestino("Manaus, AM");
                f4.setPeso(2300.0);
                f4.setTransportadora("AmazonCargo");
                f4.setStatus(Frete.StatusFrete.PENDENTE);

                repository.save(f1);
                repository.save(f2);
                repository.save(f3);
                repository.save(f4);

                System.out.println("==> TransCarga: 4 fretes de exemplo inseridos.");
            }
        };
    }
}
