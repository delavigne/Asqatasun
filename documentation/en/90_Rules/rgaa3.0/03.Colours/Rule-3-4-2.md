# RGAA 3.0 -  Rule 3.4.2

## Summary

This test consists in checking whether the contrast ratio between text
and its background is at least 7:1 for the bold, under 14px sized, textual
elements

## Business description

### Criterion

[3.4](http://asqatasun.github.io/RGAA--3.0--EN/RGAA3.0_Criteria_English_version_v1.html#crit-3-4)

### Test

[3.4.2](http://asqatasun.github.io/RGAA--3.0--EN/RGAA3.0_Criteria_English_version_v1.html#test-3-4-2)

### Description
On each Web page, do bold
    texts and images of bold text with font sizes smaller than or equal to <a href="http://asqatasun.github.io/RGAA--3.0--EN/RGAA3.0_Glossary_English_version_v1.html#mFontSize">120% of the default font size</a> (or
    1.2em) meet one of the following conditions (except
    in <a title="Particular cases for criterion 3.4" href="http://asqatasun.github.io/RGAA--3.0--EN/RGAA3.0_Particular_cases_English_version_v1.html#cpCrit3-">particular cases</a>)?
    <ul><li> The contrast ratio between text and its
   background is at least 7:1</li>
  <li>The user can display the text with
   a contrast ratio of at least 7:1, via a provided mechanism</li>
    </ul> 


### Level

**AAA**

## Technical description

### Scope

**Page**

### Decision level

**Decidable**

## Algorithm

### Selection

#### Set1

All the textual elements from the DOM with a font-size inferior or equals to 14px and bold

#### Set2

All the hidden textual elements from the DOM with a font-size inferior
or equals to 14px and bold

#### Set3

All the `<img>` tags

### Process

#### Test1

For each element of **Set1**, the contrast ratio is computed (regarding the
[contrast ratio
definition](http://www.w3.org/TR/WCAG20/#contrast-ratiodef)) to check
whether it is superior to 7.

For each element returning false in **Test1**, if the
*ALTERNATIVE_CONTRAST_MECHANISM* is set to true by the user, raise a
MessageA, raise a MessageB instead.

If an element has a contrast ratio that cannot be determined (background
defined with an image or a gradient), raise a MessageC

#### Test2

For each element of **Set2**, the contrast ratio is computed (regarding the
[contrast ratio
definition](http://www.w3.org/TR/WCAG20/#contrast-ratiodef)) to check
whether it is superior to 7.

For each element returning false in **Test2**, raise a MessageD

##### MessageA : Bad Contrast

-   code : BadContrast
-   status: Failed
-   parameter : foreground color, background color, contrast ratio, Snippet
-   present in source : yes

##### MessageB : Bad Contrast But Alternative Contrast Mechanism Present On Page

-   code : BadContrastButAlternativeContrastMechanismOnPage
-   status: Pre-Qualified
-   parameter : foreground color, background color, contrast ratio, Snippet
-   present in source : yes

##### MessageC : Not Treated Background Color

-   code : NotTreatedBackgroundColor
-   status: Pre-Qualified
-   parameter : none
-   present in source : no

##### MessageD : Bad Contrast on Hidden Element

-   code : BadContrastHiddenElement
-   status: Pre-Qualified
-   parameter : foreground color, background color, contrast ratio, Snippet
-   present in source : yes

### Analysis

#### Not Applicable

No element with a font-size inferior or equals to 14px and bold have been found (**Set1** AND **Set2** are empty)

#### Passed

All the elements with a font-size inferior or equals to 14px and bold have a correct ratio and the page contains no images (**Test1** returns true for all elements AND **Set2** AND **Set3** are empty)

#### Failed

At least one element with a font-size inferior or equals to 14px and bold have an incorrect ratio (**Test1** returns false for at least one element)

#### Pre-qualified

In all other cases


## Notes

1.  By approximation, we consider that 1.2em of the default font size is
    equivalent to 14 px.
2.  The background color (`"background"` css property), the font color
    (`"color"` css property), the font size (`"font-size"` css property) and
    the font weight (`"font-weight"` css property) are retrieved while
    fetching the page, through a javacript script. Each html element
    is parsed to extract these info, as well as its
    visibility (`"display"` css property equals to *none* or `"visibility"`
    css property equals to *hidden*) and whether it is a textual node.



##  TestCases 

[TestCases files for rule 3.4.2](https://gitlab.com/asqatasun/Asqatasun/-/tree/master/rules/rules-rgaa3.0/src/test/resources/testcases/rgaa30/Rgaa30Rule030402/) 


