package com.sctec.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.dto.EmpreendimentoResponseDTO;
import com.sctec.api.entity.Empreendimento;
import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import com.sctec.api.service.EmpreendimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpreendimentoController.class)
class EmpreendimentoSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmpreendimentoService service;

    private EmpreendimentoRequestDTO requestDTO;
    private EmpreendimentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new EmpreendimentoRequestDTO();
        requestDTO.setNomeEmpreendimento("Empresa Segura");
        requestDTO.setNomeEmpreendedor("Sentinel");
        requestDTO.setMunicipioSC("Florianópolis");
        requestDTO.setSegmento(Segmento.TECNOLOGIA);
        requestDTO.setContato("sentinel@sctec.com");
        requestDTO.setStatus(Status.ATIVO);

        responseDTO = new EmpreendimentoResponseDTO(new Empreendimento());
        responseDTO.setId(1L);
    }

    @Test
    void create_WhenNomeEmpreendimentoTooLong_ShouldReturnBadRequest() throws Exception {
        // Generate a string with 101 characters
        String longName = "a".repeat(101);
        requestDTO.setNomeEmpreendimento(longName);

        // When validation is working, this should return 400 Bad Request
        // If validation is missing, it will likely return 201 Created (since service is mocked to return success)

        when(service.create(any(EmpreendimentoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/empreendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }
}
