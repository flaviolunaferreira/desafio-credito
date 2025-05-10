package the.coyote.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import the.coyote.api.model.CreditoEntity;
import the.coyote.api.service.CreditoService;

@RestController
@RequestMapping("api/v1/credito")
@RequiredArgsConstructor
public class CreditoController {

    private final CreditoService service;

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoEntity>> consultarPorNfse(
            @PathVariable String numeroNfse,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        return ResponseEntity.ok(service.consultarPorNfse(numeroNfse, ipCliente));
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoEntity> consultarPorCredito(
            @PathVariable String numeroCredito,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        CreditoEntity credito = service.consultarPorCredito(numeroCredito, ipCliente);
        return credito != null ? ResponseEntity.ok(credito) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CreditoEntity> cadastrarCredito(
            @RequestBody CreditoEntity credito,
            @RequestHeader("X-Forwarded-For") String ipCliente) {
        return ResponseEntity.ok(service.cadastrarCredito(credito, ipCliente));
    }

}
