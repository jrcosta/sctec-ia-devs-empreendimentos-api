package com.sctec.api.entity;

import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpreendimento() {
        return nomeEmpreendimento;
    }

    public void setNomeEmpreendimento(String nomeEmpreendimento) {
        this.nomeEmpreendimento = nomeEmpreendimento;
    }

    public String getNomeEmpreendedor() {
        return nomeEmpreendedor;
    }

    public void setNomeEmpreendedor(String nomeEmpreendedor) {
        this.nomeEmpreendedor = nomeEmpreendedor;
    }

    public String getMunicipioSC() {
        return municipioSC;
    }

    public void setMunicipioSC(String municipioSC) {
        this.municipioSC = municipioSC;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
