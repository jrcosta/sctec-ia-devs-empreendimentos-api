package com.sctec.api.controller;

import com.sctec.api.dto.EmpreendimentoRequestDTO;
import com.sctec.api.dto.EmpreendimentoResponseDTO;
import com.sctec.api.service.EmpreendimentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/empreendimentos")
public class EmpreendimentoController {

    private final EmpreendimentoService service;

    public EmpreendimentoController(EmpreendimentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<EmpreendimentoResponseDTO>> findAll(Pageable pageable) {
        Page<EmpreendimentoResponseDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpreendimentoResponseDTO> findById(@PathVariable Long id) {
        EmpreendimentoResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EmpreendimentoResponseDTO> create(@Valid @RequestBody EmpreendimentoRequestDTO requestDTO) {
        EmpreendimentoResponseDTO dto = service.create(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpreendimentoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody EmpreendimentoRequestDTO requestDTO) {
        EmpreendimentoResponseDTO dto = service.update(id, requestDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
