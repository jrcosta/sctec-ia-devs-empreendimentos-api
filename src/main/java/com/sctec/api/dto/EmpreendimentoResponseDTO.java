package com.sctec.api.dto;

import com.sctec.api.entity.Empreendimento;
import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmpreendimentoResponseDTO {
    private Long id;
    private String nomeEmpreendimento;
    private String nomeEmpreendedor;
    private String municipioSC;
    private Segmento segmento;
    private String contato;
    private Status status;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public EmpreendimentoResponseDTO(Empreendimento entity) {
        this.id = entity.getId();
        this.nomeEmpreendimento = entity.getNomeEmpreendimento();
        this.nomeEmpreendedor = entity.getNomeEmpreendedor();
        this.municipioSC = entity.getMunicipioSC();
        this.segmento = entity.getSegmento();
        this.contato = entity.getContato();
        this.status = entity.getStatus();
        this.dataCadastro = entity.getDataCadastro();
        this.dataAtualizacao = entity.getDataAtualizacao();
    }
}
