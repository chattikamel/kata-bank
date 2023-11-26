# language: fr
Fonctionnalité: Opérations
  Un client de la banque kbank doit pouvoir faire des opérations sur son compte.

 Contexte:
   Etant donné les comptes bancaires suivants (identifiant, solde initial):
   |FR11223344   | 150    |
   |FR11223355   | 200    |
   |FR11223366   | 0      |
   |FR11223377   | -50    |



  Plan du Scénario: dépot d'argent
    Etant donné un client avec un compte bancaire <identifiant>
    Quand  il fait un dépot d'argent de <montantOperation> le <dateOperation>
    Alors l'opération doit être ajoutée à l'historique des opérations de compte
    Et le solde de compte doit egal à <soldeApresOperation>

    Exemples:
    | soldeApresOperation   | montantOperation  | dateOperation               |identifiant |
    | 250                   | 100               | 2050-12-10T10:15:30         |FR11223344  |
    | 1500                  | 1500              | 2024-12-03T10:15:30         |FR11223366  |
    | 20                    | 70                | 2023-12-10T10:15:30         |FR11223377  |

  Plan du Scénario: retrait d'argent
    Etant donné un client avec un compte bancaire <identifiant>
    Quand  il fait un retrait de <montantOperation> le <dateOperation>
    Alors l'opération doit être ajoutée à l'historique des opérations de compte
    Et le solde de compte doit egal à <soldeApresOperation>

    Exemples:
      | soldeApresOperation   | montantOperation  | dateOperation               |identifiant |
      | 50                    | 100               | 2050-12-10T10:15:30         |FR11223344  |
      | -1500                 | 1500              | 2024-12-03T10:15:30         |FR11223366  |
      | -120                  | 70                | 2023-12-10T10:15:30         |FR11223377  |


    Scénario:  Consultation de l'historique des opérations
      Etant donné un client avec un compte bancaire FR11223344
      Et des anciennes opérations bancaires:
        | 100               | 2050-12-10T10:15:30         |
        | 1500              | 2024-12-03T10:15:30         |
        | -70               | 2023-12-10T10:15:30         |
      Alors l'historique doit etre restitué







