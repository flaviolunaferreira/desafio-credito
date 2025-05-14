package the.coyote.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import the.coyote.api.model.CreditoEntity;
import the.coyote.api.service.CreditoService;

@RestController
@RequestMapping("api/v1/creditos")
@RequiredArgsConstructor
public class CreditoController {

    private final CreditoService service;

    @Operation(summary = "Consulta créditos por número da NFS-e")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de créditos retornada"),
        @ApiResponse(responseCode = "404", description = "Nenhum crédito encontrado")
    })
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoEntity>> consultarPorNfse(
            @PathVariable String numeroNfse,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        List<CreditoEntity> creditos = service.consultarPorNfse(numeroNfse, ipCliente);
        return ResponseEntity.ok(creditos);
    }

    @Operation(summary = "Consulta crédito por número do crédito")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Crédito retornado"),
        @ApiResponse(responseCode = "404", description = "Crédito não encontrado")
    })
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoEntity> consultarPorCredito(
            @PathVariable String numeroCredito,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        CreditoEntity credito = service.consultarPorCredito(numeroCredito, ipCliente);
        return credito != null ? ResponseEntity.ok(credito) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cadastra um novo crédito")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Crédito criado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<CreditoEntity> cadastrarCredito(
            @Valid @RequestBody CreditoEntity credito,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        CreditoEntity saved = service.cadastrarCredito(credito, ipCliente);
        return ResponseEntity.status(201).body(saved);
    }

    @Operation(summary = "Atualiza um crédito existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Crédito atualizado"),
        @ApiResponse(responseCode = "404", description = "Crédito não encontrado")
    })
    @PutMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoEntity> atualizarCredito(
            @PathVariable String numeroCredito,
            @Valid @RequestBody CreditoEntity credito,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        CreditoEntity updated = service.atualizarCredito(numeroCredito, credito, ipCliente);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Exclui um crédito")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Crédito excluído"),
        @ApiResponse(responseCode = "404", description = "Crédito não encontrado")
    })
    @DeleteMapping("/credito/{numeroCredito}")
    public ResponseEntity<Void> deletarCredito(
            @PathVariable String numeroCredito,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        service.deletarCredito(numeroCredito, ipCliente);
        return ResponseEntity.noContent().build();
    }
}