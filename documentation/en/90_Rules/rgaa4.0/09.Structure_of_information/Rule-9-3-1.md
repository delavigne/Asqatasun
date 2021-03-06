# RGAA 4.0 — Rule 9.3.1

## Summary

No-check rule

## Business description

### Criterion

[9.3](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#crit-9-3)

### Test

[9.3.1](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/criteres/#test-9-3-1)

### Description

> Dans chaque page web, les informations regroupées visuellement sous forme de [liste](https://www.numerique.gouv.fr/publications/rgaa-accessibilite/methode/glossaire/#listes) non ordonnée vérifient-elles une de ces conditions ?
> 
> * La liste utilise les balises HTML `<ul>` et `<li>`.
> * La liste utilise les attributs WAI-ARIA `role="list"` et `"listitem"`.

#### Technical notes (criterion 9.3)

> Les attributs WAI-ARIA `role="list"` et `"listitem"` peuvent nécessiter l’utilisation des attributs WAI-ARIA `aria-setsize` et `aria-posinset` dans le cas où l’ensemble de la liste n’est pas disponible via le DOM généré au moment de la consultation.
> 
> Les attributs WAI-ARIA `role="tree"`, `"tablist"`, `"menu"`, `"combobox"` et `"listbox"` ne sont pas équivalents à une liste HTML `<ul>` ou `<ol>`.
> 
> Voir : [The roles model - list](https://www.w3.org/TR/wai-aria/#list)

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

- [TestCases files for rule 9.3.1](https://gitlab.com/asqatasun/Asqatasun/-/tree/master/rules/rules-rgaa4.0/src/test/resources/testcases/rgaa40/Rgaa40Rule090301/)
- [Unit test file for rule 9.3.1](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/test/java/org/asqatasun/rules/rgaa40/Rgaa40Rule090301Test.java)
- [Class file for rule 9.3.1](https://gitlab.com/asqatasun/Asqatasun/-/blob/master/rules/rules-rgaa4.0/src/main/java/org/asqatasun/rules/rgaa40/Rgaa40Rule090301.java)


