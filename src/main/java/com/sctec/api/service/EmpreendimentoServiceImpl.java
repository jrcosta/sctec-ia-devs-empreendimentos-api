package com.sctec.api.service;

import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.dto.EmpreendimentoResponseDTO;
import com.sctec.api.entity.Empreendimento;
import com.sctec.api.exception.ResourceNotFoundException;
import com.sctec.api.repository.EmpreendimentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpreendimentoServiceImpl implements EmpreendimentoService {

    private final EmpreendimentoRepository repository;

    public EmpreendimentoServiceImpl(EmpreendimentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public EmpreendimentoResponseDTO create(EmpreendimentoRequestDTO requestDTO) {
        Empreendimento entity = new Empreendimento();
        copyDtoToEntity(requestDTO, entity);
        entity = repository.save(entity);
        return new EmpreendimentoResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpreendimentoResponseDTO> findAll(Pageable pageable) {
        Page<Empreendimento> page = repository.findAll(pageable);
        return page.map(EmpreendimentoResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpreendimentoResponseDTO findById(Long id) {
        Empreendimento entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado para o id " + id));
        return new EmpreendimentoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EmpreendimentoResponseDTO update(Long id, EmpreendimentoRequestDTO requestDTO) {
        Empreendimento entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado para o id " + id));
        copyDtoToEntity(requestDTO, entity);
        entity = repository.save(entity);
        return new EmpreendimentoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Empreendimento não encontrado para o id " + id);
        }
        repository.deleteById(id);
    }

    private void copyDtoToEntity(EmpreendimentoRequestDTO dto, Empreendimento entity) {
        entity.setNomeEmpreendimento(dto.getNomeEmpreendimento());
        entity.setNomeEmpreendedor(dto.getNomeEmpreendedor());
        entity.setMunicipioSC(dto.getMunicipioSC());
        entity.setSegmento(dto.getSegmento());
        entity.setContato(dto.getContato());
        entity.setStatus(dto.getStatus());
    }
}
