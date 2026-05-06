package br.com.transcarga.persistencia;

import br.com.transcarga.model.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FreteRepository - Camada de Persistência (DAO com Spring Data JPA)
 * Substitui o FreteDAO manual do tutorial original.
 * O Spring Data JPA gera automaticamente as implementações dos métodos.
 */
@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {

    // Busca fretes por destino (ignorando maiúsculas/minúsculas)
    List<Frete> findByDestinoContainingIgnoreCase(String destino);

    // Busca fretes por transportadora
    List<Frete> findByTransportadoraContainingIgnoreCase(String transportadora);

    // Busca fretes por status
    List<Frete> findByStatus(Frete.StatusFrete status);

    // Busca por destino OU transportadora (pesquisa geral)
    @Query("SELECT f FROM Frete f WHERE " +
           "LOWER(f.destino) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(f.transportadora) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Frete> buscarPorTermo(@Param("termo") String termo);

    // Conta fretes por status
    long countByStatus(Frete.StatusFrete status);

    // Soma total de peso de todos os fretes
    @Query("SELECT COALESCE(SUM(f.peso), 0) FROM Frete f")
    Double somarPesoTotal();
}
