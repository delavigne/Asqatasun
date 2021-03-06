# RGAA 4.0 — Rule 9.1.3

## Summary

No-check rule

## Business description

### Criterion

[9.1](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#crit-9-1)

### Test

[9.1.3](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#test-9-1-3)

### Description

> Dans chaque page web, chaque passage de texte constituant un [titre](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#titre) est-il structuré à l’aide d’une balise `<hx>` ou d’une balise possédant un attribut WAI-ARIA `role="heading"` associé à un attribut WAI-ARIA `aria-level` ?

#### Technical notes (criterion 9.1)

> WAI-ARIA permet de définir des titres via le rôle `heading` et l’attribut `aria-level` (indication du niveau de titre). Bien qu’il soit préférable d’utiliser l’élément de titre natif en HTML `<hx>`, l’utilisation du rôle WAI-ARIA `heading` est compatible avec l’accessibilité.
> 
> Bien que la spécification HTML5 autorise l’utilisation exclusive de titres de niveau 1 (`h1`), le manque de support des technologies d’assistance oblige à utiliser une hiérarchie de titres pertinente.

### Level

**A**


## Technical description

### Scope

**Page**

### Decision level

@@@TODO


## Algorithm

### Selection

None

### Process

None

### Analysis

#### Not Tested

In all cases


## Files

- [TestCases files for rule 9.1.3](https://gitlab.com/asqatasun/Asqatasun/-/tree/master/rules/rules-rgaa4.0/src/test/resources/testcases/rgaa40/Rgaa40Rule090103/)
- [Unit test file for rule 9.1.3](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/test/java/org/asqatasun/rules/rgaa40/Rgaa40Rule090103Test.java)
- [Class file for rule 9.1.3](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/main/java/org/asqatasun/rules/rgaa40/Rgaa40Rule090103.java)


