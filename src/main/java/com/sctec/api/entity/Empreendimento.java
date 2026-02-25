package com.sctec.api.entity;

import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empreendimentos")
public class Empreendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeEmpreendimento;

    @Column(nullable = false, length = 100)
    private String nomeEmpreendedor;

    @Column(nullable = false, length = 100)
    private String municipioSC;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Segmento segmento;

    @Column(nullable = false, length = 50)
    private String contato;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
