package com.sctec.api.dto;

import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpreendimentoRequestDTO {

    @NotBlank(message = "O nome do empreendimento não pode estar em branco")
    @jakarta.validation.constraints.Size(max = 100, message = "O nome do empreendimento deve ter no máximo 100 caracteres")
    private String nomeEmpreendimento;

    @NotBlank(message = "O nome do empreendedor não pode estar em branco")
    @jakarta.validation.constraints.Size(max = 100, message = "O nome do empreendedor deve ter no máximo 100 caracteres")
    private String nomeEmpreendedor;

    @NotBlank(message = "O município não pode estar em branco")
    @jakarta.validation.constraints.Size(max = 100, message = "O município deve ter no máximo 100 caracteres")
    private String municipioSC;

    @NotNull(message = "O segmento é obrigatório")
    private Segmento segmento;

    @NotBlank(message = "O contato não pode estar em branco")
    @jakarta.validation.constraints.Size(max = 50, message = "O contato deve ter no máximo 50 caracteres")
    private String contato;

    @NotNull(message = "O status é obrigatório")
    private Status status;

}
