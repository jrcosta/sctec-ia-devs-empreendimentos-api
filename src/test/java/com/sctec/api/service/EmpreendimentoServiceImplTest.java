package com.sctec.api.service;

import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.dto.EmpreendimentoResponseDTO;
import com.sctec.api.entity.Empreendimento;
import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import com.sctec.api.exception.ResourceNotFoundException;
import com.sctec.api.repository.EmpreendimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpreendimentoServiceImplTest {

    @Mock
    private EmpreendimentoRepository repository;

    @InjectMocks
    private EmpreendimentoServiceImpl service;

    private Empreendimento empreendimento;
    private EmpreendimentoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        empreendimento = new Empreendimento();
        empreendimento.setId(1L);
        empreendimento.setNomeEmpreendimento("Empresa Teste");
        empreendimento.setNomeEmpreendedor("João Silva");
        empreendimento.setMunicipioSC("Florianópolis");
        empreendimento.setSegmento(Segmento.TECNOLOGIA);
        empreendimento.setContato("joao@teste.com");
        empreendimento.setStatus(Status.ATIVO);
        empreendimento.setDataCadastro(LocalDateTime.now());

        requestDTO = new EmpreendimentoRequestDTO();
        requestDTO.setNomeEmpreendimento("Empresa Teste Editada");
        requestDTO.setNomeEmpreendedor("João Silva 2");
        requestDTO.setMunicipioSC("Joinville");
        requestDTO.setSegmento(Segmento.SERVICOS);
        requestDTO.setContato("joao2@teste.com");
        requestDTO.setStatus(Status.INATIVO);
    }

    @Test
    void create_ShouldReturnResponseDTO() {
        when(repository.save(any(Empreendimento.class))).thenReturn(empreendimento);

        EmpreendimentoResponseDTO response = service.create(requestDTO);

        assertNotNull(response);
        assertEquals(empreendimento.getId(), response.getId());
        assertEquals(empreendimento.getNomeEmpreendimento(), response.getNomeEmpreendimento());
        verify(repository, times(1)).save(any(Empreendimento.class));
    }

    @Test
    void findAll_ShouldReturnPageOfResponseDTO() {
        Page<Empreendimento> page = new PageImpl<>(List.of(empreendimento));
        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<EmpreendimentoResponseDTO> response = service.findAll(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void findById_WhenIdExists_ShouldReturnResponseDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(empreendimento));

        EmpreendimentoResponseDTO response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenIdDoesNotExist_ShouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void update_WhenIdExists_ShouldReturnResponseDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(empreendimento));
        when(repository.save(any(Empreendimento.class))).thenReturn(empreendimento);

        EmpreendimentoResponseDTO response = service.update(1L, requestDTO);

        assertNotNull(response);
        assertEquals(empreendimento.getId(), response.getId());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Empreendimento.class));
    }

    @Test
    void update_WhenIdDoesNotExist_ShouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, requestDTO));
        verify(repository, times(1)).findById(99L);
        verify(repository, never()).save(any(Empreendimento.class));
    }

    @Test
    void delete_WhenIdExists_ShouldDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenIdDoesNotExist_ShouldThrowException() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
        verify(repository, times(1)).existsById(99L);
        verify(repository, never()).deleteById(anyLong());
    }
}
