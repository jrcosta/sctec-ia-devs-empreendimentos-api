package com.sctec.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.dto.EmpreendimentoResponseDTO;
import com.sctec.api.entity.Empreendimento;
import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import com.sctec.api.exception.ResourceNotFoundException;
import com.sctec.api.service.EmpreendimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpreendimentoController.class)
class EmpreendimentoControllerTest {

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
        requestDTO.setNomeEmpreendimento("Empresa Teste");
        requestDTO.setNomeEmpreendedor("João");
        requestDTO.setMunicipioSC("Florianópolis");
        requestDTO.setSegmento(Segmento.TECNOLOGIA);
        requestDTO.setContato("joao@teste.com");
        requestDTO.setStatus(Status.ATIVO);

        Empreendimento entity = new Empreendimento();
        entity.setId(1L);
        entity.setNomeEmpreendimento("Empresa Teste");
        entity.setNomeEmpreendedor("João");
        entity.setMunicipioSC("Florianópolis");
        entity.setSegmento(Segmento.TECNOLOGIA);
        entity.setContato("joao@teste.com");
        entity.setStatus(Status.ATIVO);
        entity.setDataCadastro(LocalDateTime.now());

        responseDTO = new EmpreendimentoResponseDTO(entity);
    }

    @Test
    void create_ShouldReturnCreated() throws Exception {
        when(service.create(any(EmpreendimentoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/empreendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeEmpreendimento").value("Empresa Teste"));

        verify(service, times(1)).create(any(EmpreendimentoRequestDTO.class));
    }

    @Test
    void create_WhenInvalidRequest_ShouldReturnBadRequest() throws Exception {
        requestDTO.setNomeEmpreendimento(""); // Invalid, Triggers @NotBlank

        mockMvc.perform(post("/api/v1/empreendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.details.nomeEmpreendimento").exists());

        verify(service, never()).create(any(EmpreendimentoRequestDTO.class));
    }

    @Test
    void findById_WhenIdExists_ShouldReturnOk() throws Exception {
        when(service.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/empreendimentos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(service, times(1)).findById(1L);
    }

    @Test
    void findById_WhenIdDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(service.findById(99L)).thenThrow(new ResourceNotFoundException("Empreendimento não encontrado"));

        mockMvc.perform(get("/api/v1/empreendimentos/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));

        verify(service, times(1)).findById(99L);
    }

    @Test
    void findAll_ShouldReturnOk() throws Exception {
        Page<EmpreendimentoResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        when(service.findAll(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/empreendimentos")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));

        verify(service, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void update_WhenValidRequest_ShouldReturnOk() throws Exception {
        when(service.update(eq(1L), any(EmpreendimentoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/empreendimentos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(service, times(1)).update(eq(1L), any(EmpreendimentoRequestDTO.class));
    }

    @Test
    void delete_WhenIdExists_ShouldReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/v1/empreendimentos/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
