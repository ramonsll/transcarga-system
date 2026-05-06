package br.com.transcarga.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entidade Frete - Camada de Persistência (Model)
 * Representa um frete no sistema TransCarga.
 */
@Entity
@Table(name = "fretes")
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Destino é obrigatório")
    @Size(min = 3, max = 150, message = "Destino deve ter entre 3 e 150 caracteres")
    @Column(nullable = false, length = 150)
    private String destino;

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que zero")
    @Column(nullable = false)
    private Double peso;

    @NotBlank(message = "Transportadora é obrigatória")
    @Size(min = 3, max = 100, message = "Transportadora deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String transportadora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusFrete status = StatusFrete.PENDENTE;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }

    // -------------------------------------------------------
    // Enum de Status
    // -------------------------------------------------------
    public enum StatusFrete {
        PENDENTE("Pendente"),
        EM_TRANSITO("Em Trânsito"),
        ENTREGUE("Entregue"),
        CANCELADO("Cancelado");

        private final String descricao;

        StatusFrete(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    // -------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public String getTransportadora() { return transportadora; }
    public void setTransportadora(String transportadora) { this.transportadora = transportadora; }

    public StatusFrete getStatus() { return status; }
    public void setStatus(StatusFrete status) { this.status = status; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getDataCadastroFormatada() {
        if (dataCadastro == null) return "-";
        return dataCadastro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "Frete{id=" + id + ", destino='" + destino + "', peso=" + peso +
               ", transportadora='" + transportadora + "', status=" + status + "}";
    }
}
