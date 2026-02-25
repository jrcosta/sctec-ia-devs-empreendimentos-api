package com.sctec.api.service;

import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.dto.EmpreendimentoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface EmpreendimentoService {

    EmpreendimentoResponseDTO create(EmpreendimentoRequestDTO requestDTO);

    Page<EmpreendimentoResponseDTO> findAll(@NonNull Pageable pageable);

    EmpreendimentoResponseDTO findById(@NonNull Long id);

    EmpreendimentoResponseDTO update(@NonNull Long id, EmpreendimentoRequestDTO requestDTO);

    void delete(@NonNull Long id);

}
