package com.sctec.api;

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
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testInputLengthValidation() throws Exception {
        EmpreendimentoRequestDTO requestDTO = new EmpreendimentoRequestDTO();
        // Create a string longer than 100 characters (limit in Entity)
        String longString = "a".repeat(150);
        requestDTO.setNomeEmpreendimento(longString);
        requestDTO.setNomeEmpreendedor("João");
        requestDTO.setMunicipioSC("Florianópolis");
        requestDTO.setSegmento(Segmento.TECNOLOGIA);
        requestDTO.setContato("joao@teste.com");
        requestDTO.setStatus(Status.ATIVO);

        // Currently this might return 500 because of DB exception, or 201 if H2 doesn't enforce length by default without config (it usually does)
        // We want it to be 400 Bad Request due to validation
        mockMvc.perform(post("/api/v1/empreendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }
}
