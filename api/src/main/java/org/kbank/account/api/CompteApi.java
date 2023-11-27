package org.kbank.account.api;

import org.kbank.account.model.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comptes")
public class CompteApi {

    @Autowired
    CompteService compteService;

    @PostMapping(value = "/operations/{identifiant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void ajouterOperation(@PathVariable("identifiant") final String identifiant, @RequestBody Operation operation) {
        operation.setIdentifiantCompte(identifiant);
        compteService.traiter(toOperationModel(operation));
    }

    @ResponseBody
    @GetMapping(value = "/operations/{identifiant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Operation> listerOperations(@PathVariable("identifiant") final String identifiant) {
        return compteService.listerOperations(identifiant)
                .map(toOperationApi)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping(value = "/solde/{identifiant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal recupererSolde(@PathVariable("identifiant") final String identifiant) {
        return compteService.solde(identifiant);
    }

    private static Function<org.kbank.account.model.Operation, Operation> toOperationApi = op -> Operation.builder()
            .id(op.getUiid().toString())
            .identifiantCompte(op.getIdentifiantCompte())
            .date(op.getDate())
            .montant(op.getMontant())
            .build();

    private static org.kbank.account.model.Operation toOperationModel(Operation op) {
        return new org.kbank.account.model.Operation(
                op.getIdentifiantCompte(),
                op.getMontant(),
                op.getDate());
    }

}


