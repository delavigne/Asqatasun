# RGAA 4.0 — Rule 1.6.7

## Summary

No-check rule

## Business description

### Criterion

[1.6](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#crit-1-6)

### Test

[1.6.7](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#test-1-6-7)

### Description

> Chaque image bitmap (balise `<canvas>`), [porteuse d’information](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#image-porteuse-d-information), qui nécessite une [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image), vérifie-t-elle une de ces conditions ?
> 
> * Il existe un attribut WAI-ARIA `aria-label` contenant l’[alternative textuelle](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#alternative-textuelle-image) et une référence à une [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image) adjacente.
> * Il existe un attribut WAI-ARIA `aria-labelledby` associant un [passage de texte](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#passage-de-texte-lie-par-aria-labelledby-ou-aria-describedby) faisant office d’[alternative textuelle](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#alternative-textuelle-image) et un autre faisant office de [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image).
> * Il existe un contenu textuel entre `<canvas>` et `</canvas>` faisant référence à une [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image) adjacente à l’image bitmap.
> * Il existe un contenu textuel entre `<canvas>` et `</canvas>` faisant office de [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image).
> * Il existe un [lien ou bouton adjacent](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#lien-ou-bouton-adjacent) permettant d’accéder à la [description détaillée](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#description-detaillee-image).

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

- [TestCases files for rule 1.6.7](https://gitlab.com/asqatasun/Asqatasun/-/tree/master/rules/rules-rgaa4.0/src/test/resources/testcases/rgaa40/Rgaa40Rule010607/)
- [Unit test file for rule 1.6.7](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/test/java/org/asqatasun/rules/rgaa40/Rgaa40Rule010607Test.java)
- [Class file for rule 1.6.7](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/main/java/org/asqatasun/rules/rgaa40/Rgaa40Rule010607.java)


