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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/empreendimentos")
@Tag(name = "Empreendimentos", description = "Gerenciamento do CRUD de Empreendimentos SCTEC")
public class EmpreendimentoController {

    private final EmpreendimentoService service;

    public EmpreendimentoController(EmpreendimentoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os empreendimentos", description = "Retorna uma lista paginada de todos os empreendimentos cadastrados")
    public ResponseEntity<Page<EmpreendimentoResponseDTO>> findAll(Pageable pageable) {
        Page<EmpreendimentoResponseDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empreendimento pelo ID", description = "Pesquisa e devolve as informações de um único empreendimento passando seu ID de registro.")
    public ResponseEntity<EmpreendimentoResponseDTO> findById(@PathVariable Long id) {
        EmpreendimentoResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Criar novo empreendimento", description = "Cadastra um novo empreendimento. Requer validação de formulário completo via DTO.")
    public ResponseEntity<EmpreendimentoResponseDTO> create(@Valid @RequestBody EmpreendimentoRequestDTO requestDTO) {
        EmpreendimentoResponseDTO dto = service.create(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar as informações", description = "Permite alterar os dados de um empreendimento já criado (ex: inativar no status).")
    public ResponseEntity<EmpreendimentoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody EmpreendimentoRequestDTO requestDTO) {
        EmpreendimentoResponseDTO dto = service.update(id, requestDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um empreendimento", description = "Deleta fisicamente um registro do sistema após verificar sua existência.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
