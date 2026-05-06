package br.com.transcarga.negocios;

import br.com.transcarga.model.Frete;
import br.com.transcarga.persistencia.FreteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * FreteService - Camada de Negócios
 * Contém a lógica de negócio do sistema TransCarga.
 * Equivale ao processamento que estava no FreteServlet original.
 */
@Service
@Transactional
public class FreteService {

    private final FreteRepository freteRepository;

    public FreteService(FreteRepository freteRepository) {
        this.freteRepository = freteRepository;
    }

    // -------------------------------------------------------
    // Operações CRUD
    // -------------------------------------------------------

    public Frete cadastrarFrete(Frete frete) {
        return freteRepository.save(frete);
    }

    @Transactional(readOnly = true)
    public List<Frete> listarTodos() {
        return freteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Frete> buscarPorId(Long id) {
        return freteRepository.findById(id);
    }

    public Frete atualizarFrete(Frete frete) {
        return freteRepository.save(frete);
    }

    public void excluirFrete(Long id) {
        freteRepository.deleteById(id);
    }

    // -------------------------------------------------------
    // Buscas
    // -------------------------------------------------------

    @Transactional(readOnly = true)
    public List<Frete> buscarPorTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }
        return freteRepository.buscarPorTermo(termo.trim());
    }

    @Transactional(readOnly = true)
    public List<Frete> buscarPorStatus(Frete.StatusFrete status) {
        return freteRepository.findByStatus(status);
    }

    // -------------------------------------------------------
    // Estatísticas (para o dashboard)
    // -------------------------------------------------------

    @Transactional(readOnly = true)
    public long totalFretes() {
        return freteRepository.count();
    }

    @Transactional(readOnly = true)
    public double pesoTotal() {
        Double total = freteRepository.somarPesoTotal();
        return total != null ? total : 0.0;
    }

    @Transactional(readOnly = true)
    public long fretesPendentes() {
        return freteRepository.countByStatus(Frete.StatusFrete.PENDENTE);
    }

    @Transactional(readOnly = true)
    public long fretesEmTransito() {
        return freteRepository.countByStatus(Frete.StatusFrete.EM_TRANSITO);
    }

    @Transactional(readOnly = true)
    public long fretesEntregues() {
        return freteRepository.countByStatus(Frete.StatusFrete.ENTREGUE);
    }
}
