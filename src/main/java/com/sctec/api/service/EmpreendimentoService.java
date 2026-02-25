package com.sctec.api.service;

import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.dto.EmpreendimentoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpreendimentoService {

    EmpreendimentoResponseDTO create(EmpreendimentoRequestDTO requestDTO);

    Page<EmpreendimentoResponseDTO> findAll(Pageable pageable);

    EmpreendimentoResponseDTO findById(Long id);

    EmpreendimentoResponseDTO update(Long id, EmpreendimentoRequestDTO requestDTO);

    void delete(Long id);

}
