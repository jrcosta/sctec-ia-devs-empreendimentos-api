package com.sctec.api.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sctec.api.enums.Segmento;
import com.sctec.api.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    // ========================
    // ResourceNotFoundException
    // ========================

    @Test
    void handleResourceNotFoundException_ShouldReturn404WithMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Empreendimento não encontrado para o id 99");

        ResponseEntity<Map<String, Object>> response = handler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals("Empreendimento não encontrado para o id 99", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    // ===========================
    // MethodArgumentNotValidException
    // ===========================

    @Test
    void handleValidationExceptions_ShouldReturn400WithFieldErrors() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "empreendimentoRequestDTO");
        bindingResult.addError(new FieldError("empreendimentoRequestDTO", "nomeEmpreendimento",
                "O nome do empreendimento não pode estar em branco"));
        bindingResult.addError(new FieldError("empreendimentoRequestDTO", "contato",
                "O contato não pode estar em branco"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals("Validation Failed", body.get("message"));

        @SuppressWarnings("unchecked")
        Map<String, String> details = (Map<String, String>) body.get("details");
        assertNotNull(details);
        assertEquals("O nome do empreendimento não pode estar em branco", details.get("nomeEmpreendimento"));
        assertEquals("O contato não pode estar em branco", details.get("contato"));
    }

    // ===============================
    // HttpMessageNotReadableException
    // ===============================

    @Test
    void handleHttpMessageNotReadable_WhenInvalidEnumSegmento_ShouldReturnFieldSpecificMessage() {
        // Simula InvalidFormatException para enum Segmento
        InvalidFormatException cause = new InvalidFormatException(
                null, "Valor inválido", "NADA", Segmento.class);
        cause.prependPath(new JsonMappingException.Reference(null, "segmento"));

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON parse error", cause, mock(HttpInputMessage.class));

        ResponseEntity<Map<String, Object>> response = handler.handleHttpMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals(
                "Valor inválido 'NADA' para o campo 'segmento'. Consulte a documentação da API para os valores aceitos.",
                body.get("message"));
    }

    @Test
    void handleHttpMessageNotReadable_WhenInvalidEnumStatus_ShouldReturnFieldSpecificMessage() {
        // Simula InvalidFormatException para enum Status
        InvalidFormatException cause = new InvalidFormatException(
                null, "Valor inválido", "INVALIDO", Status.class);
        cause.prependPath(new JsonMappingException.Reference(null, "status"));

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON parse error", cause, mock(HttpInputMessage.class));

        ResponseEntity<Map<String, Object>> response = handler.handleHttpMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(
                "Valor inválido 'INVALIDO' para o campo 'status'. Consulte a documentação da API para os valores aceitos.",
                body.get("message"));
    }

    @Test
    void handleHttpMessageNotReadable_WhenInvalidEnumWithEmptyPath_ShouldReturnDesconhecido() {
        // Simula InvalidFormatException para enum mas sem path (campo desconhecido)
        InvalidFormatException cause = new InvalidFormatException(
                null, "Valor inválido", "XYZ", Segmento.class);
        // Não adiciona path — simula cenário de path vazio

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON parse error", cause, mock(HttpInputMessage.class));

        ResponseEntity<Map<String, Object>> response = handler.handleHttpMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(
                "Valor inválido 'XYZ' para o campo 'desconhecido'. Consulte a documentação da API para os valores aceitos.",
                body.get("message"));
    }

    @Test
    void handleHttpMessageNotReadable_WhenGenericCause_ShouldReturnGenericMessage() {
        // Simula exceção com causa genérica (não InvalidFormatException)
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON parse error", new RuntimeException("malformed JSON"), mock(HttpInputMessage.class));

        ResponseEntity<Map<String, Object>> response = handler.handleHttpMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("O corpo da requisição contém um formato inválido ou não pôde ser lido.", body.get("message"));
    }

    @Test
    void handleHttpMessageNotReadable_WhenNullCause_ShouldReturnGenericMessage() {
        // Simula exceção sem causa
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON parse error", (Throwable) null, mock(HttpInputMessage.class));

        ResponseEntity<Map<String, Object>> response = handler.handleHttpMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("O corpo da requisição contém um formato inválido ou não pôde ser lido.", body.get("message"));
    }

    @Test
    void handleHttpMessageNotReadable_WhenInvalidFormatButNotEnum_ShouldReturnGenericMessage() {
        // Simula InvalidFormatException para tipo não-enum (ex: Long)
        InvalidFormatException cause = new InvalidFormatException(
                null, "Formato inválido", "abc", Long.class);
        cause.prependPath(new JsonMappingException.Reference(null, "id"));

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON parse error", cause, mock(HttpInputMessage.class));

        ResponseEntity<Map<String, Object>> response = handler.handleHttpMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        // Não é enum, então deve retornar mensagem genérica
        assertEquals("O corpo da requisição contém um formato inválido ou não pôde ser lido.", body.get("message"));
    }

    // ====================
    // Exception (catch-all)
    // ====================

    @Test
    void handleAllUncaughtException_ShouldReturn500WithGenericMessage() {
        Exception ex = new RuntimeException("Erro inesperado no sistema");

        ResponseEntity<Map<String, Object>> response = handler.handleAllUncaughtException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("An unexpected error occurred", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }
}
