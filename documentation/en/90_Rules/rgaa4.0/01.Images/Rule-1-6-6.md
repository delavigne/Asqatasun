# RGAA 4.0 — Rule 1.6.6

## Summary

No-check rule

## Business description

### Criterion

[1.6](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#crit-1-6)

### Test

[1.6.6](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#test-1-6-6)

### Description

> Pour chaque image vectorielle (balise `<svg>`) [porteuse d’information](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#image-porteuse-d-information), ayant une [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image), la référence éventuelle à la [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image) dans l’attribut WAI-ARIA `aria-label` et la [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image) associée par l’attribut WAI-ARIA `aria-labelledby` ou `aria-describedby` sont-elles correctement restituées par les technologies d’assistance ?

#### Technical notes (criterion 1.6)

> Dans le cas du SVG, le manque de support de l’élément `<title>` et `<desc>` par les technologies d’assistance crée une difficulté dans le cas de l’implémentation de l’[alternative textuelle](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#alternative-textuelle-image) de l’image et de sa [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image). Dans ce cas, il est recommandé d’utiliser l’attribut WAI-ARIA `aria-label` pour implémenter à la fois l’[alternative textuelle](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#alternative-textuelle-image) courte et la référence à [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image) adjacente ou l’attribut WAI-ARIA `aria-labelledby` pour associer les passages de texte faisant office d’alternative courte et de [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image).
> 
> L’utilisation de l’attribut WAI-ARIA `aria-describedby` n’est pas possible pour lier une image à sa [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image) par manque de support des technologies d’assistance.
> 
> La [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image) adjacente peut être implémentée via une balise `<figcaption>`, dans ce cas le [critère 1.9](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#crit-1-9) doit être vérifié (utilisation de `<figure>` et des attributs WAI-ARIA `role="figure"` et `aria-label`, notamment).

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

- [TestCases files for rule 1.6.6](https://gitlab.com/asqatasun/Asqatasun/-/tree/master/rules/rules-rgaa4.0/src/test/resources/testcases/rgaa40/Rgaa40Rule010606/)
- [Unit test file for rule 1.6.6](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/test/java/org/asqatasun/rules/rgaa40/Rgaa40Rule010606Test.java)
- [Class file for rule 1.6.6](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/main/java/org/asqatasun/rules/rgaa40/Rgaa40Rule010606.java)


