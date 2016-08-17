# Text-Understanding
Predicate Argument Annotations for Natural Language text.

About
----------

This software annotates predicate-argument structures of natural-language texts, by utilizing information from the text's syntactic-analysis.

For example, given the sentence "*I have a computer that can process natural languages.*", the software extracts the following:

**have**  
&nbsp; &nbsp; &nbsp; (SUBJECT) I: I  
&nbsp; &nbsp; &nbsp; (OBJECT) computer: a computer that can process natural languages

**process**  
&nbsp; &nbsp; &nbsp; (SUBJECT) computer: a computer  
&nbsp; &nbsp; &nbsp; (OBJECT) natural languages: natural languages  

Predicate-argument structures can be thought of a shallower approach than *Semantic Role Labeling*, yet more semantically expressive than syntactic parse-trees.

Usage
--------
TBD



