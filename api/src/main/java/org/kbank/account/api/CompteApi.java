package org.kbank.account.api;

import org.kbank.account.model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comptes")
public class CompteApi {

    @Autowired
    ApiCompteRepository apiCompteRepository;

    @PostMapping(value = "/operations/{identifiant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void ajouterOperation(@PathVariable("identifiant") final String identifiant, @RequestBody Operation operation) {
        Compte compte = new Compte(identifiant, apiCompteRepository);
        compte.traiter(toOperationModel(operation));
    }

    @ResponseBody
    @GetMapping(value = "/operations/{identifiant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Operation> listerOperations(@PathVariable("identifiant") final String identifiant) {
        return new Compte(identifiant, apiCompteRepository)
                .listerOperations()
                .map(toOperationApi)
                .collect(Collectors.toList());
    }

    private static Function<org.kbank.account.model.Operation, Operation> toOperationApi = op -> Operation.builder()
            .id(op.getUiid().toString())
            .identifiantCompte(op.getIdentifiantCompte())
            .date(op.getDate())
            .montant(op.getMontant())
            .build();

    private static org.kbank.account.model.Operation toOperationModel(Operation op) {
        return new org.kbank.account.model.Operation(op.getMontant(), op.getDate());
    }

}


