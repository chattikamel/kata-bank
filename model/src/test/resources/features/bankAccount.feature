# language: fr
Fonctionnalité: Opérations
  Un client de la banque kbank doit pouvoir faire des opérations sur son compte.


  Scénario: dépot d'argent
    Etant donné un client avec un compte bancaire "FR11223344" et un solde à 100
    Quand  il fait un dépot d'argent de 50 le 2011-12-03T10:15:30
    Alors l'opération doit être ajoutée à l'historique des opérations de compte
    Et le solde de compte doit etre mis à jour





