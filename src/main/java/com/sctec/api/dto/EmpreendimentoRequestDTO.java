package com.sctec.api.dto;

import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpreendimentoRequestDTO {

    @NotBlank(message = "O nome do empreendimento não pode estar em branco")
    private String nomeEmpreendimento;

    @NotBlank(message = "O nome do empreendedor não pode estar em branco")
    private String nomeEmpreendedor;

    @NotBlank(message = "O município não pode estar em branco")
    private String municipioSC;

    @NotNull(message = "O segmento é obrigatório")
    private Segmento segmento;

    @NotBlank(message = "O contato não pode estar em branco")
    private String contato;

    @NotNull(message = "O status é obrigatório")
    private Status status;

}
