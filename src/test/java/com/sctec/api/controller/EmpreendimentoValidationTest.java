package com.sctec.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmpreendimentoValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnBadRequestWhenNameExceedsLength() throws Exception {
        EmpreendimentoRequestDTO request = new EmpreendimentoRequestDTO();
        request.setNomeEmpreendimento("A".repeat(101)); // Exceeds 100
        request.setNomeEmpreendedor("Valid Name");
        request.setMunicipioSC("Valid City");
        request.setSegmento(Segmento.TECNOLOGIA);
        request.setContato("Valid Contact");
        request.setStatus(Status.ATIVO);

        mockMvc.perform(post("/api/v1/empreendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenContactExceedsLength() throws Exception {
        EmpreendimentoRequestDTO request = new EmpreendimentoRequestDTO();
        request.setNomeEmpreendimento("Valid Name");
        request.setNomeEmpreendedor("Valid Name");
        request.setMunicipioSC("Valid City");
        request.setSegmento(Segmento.TECNOLOGIA);
        request.setContato("A".repeat(51)); // Exceeds 50
        request.setStatus(Status.ATIVO);

        mockMvc.perform(post("/api/v1/empreendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
