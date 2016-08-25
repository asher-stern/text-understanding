# Text-Understanding
Predicate Argument Annotations for Natural Language text.

# About


This software annotates predicate-argument structures of natural-language texts, by utilizing information from the text's syntactic-analysis.

For example, given the sentence "*I have a computer that can process natural languages.*", the software extracts the following:

**have**  
&nbsp; &nbsp; &nbsp; (SUBJECT) I  
&nbsp; &nbsp; &nbsp; (OBJECT) computer

**process**  
&nbsp; &nbsp; &nbsp; (SUBJECT) computer  
&nbsp; &nbsp; &nbsp; (OBJECT) natural languages  

Predicate-argument structures can be thought of as a shallower approach than *Semantic Role Labeling*, yet more semantically expressive than syntactic parse-trees.

The annotation tool is named **PASTA**, which stands for **P**redicate **A**rgument **S**tructure **A**nnotator.

# Usage

## Embedding

Assuming a Maven project, the following dependency should be added to the POM file
```xml
<dependency>
    <groupId>com.github.asher-stern</groupId>
    <artifactId>text-understanding</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Demo

The project includes a demo program: `com.as.text_understanding.pasta.DemoPasta`. This is a command-line program which takes no arguments. It reads sentences interactively from the standard input, and prints their predicate-argument structures.


## API

PASTA can be used in two ways: as a stand-alone tool, or as a [UIMA](https://uima.apache.org/) annotator.

### Stand-alone

As a stand-alone tool, the user has to provide a constituency parse-tree as the input. The parse-tree should be in the format of `com.as.text_understanding.representation.tree.TreeNode`, and be converted to `TreeTravelNode` (using the static method `TreeTravelNode.createFromTree()`, which is the input-argument of `com.as.text_understanding.pasta.Pasta`

An easy way to obtain such a parse-tree is to use UIMA DKPro libarary just for the syntactic analysis, and convert it to `TreeNode` using `TreeBuilderFromDkpro`. This is easy since all the required libararies are included in the project's POM, and no models or parameters are needed (they are all provided seemlessly by DKPro). An example is provided in `com.as.text_understanding.pasta.DemoPasta`.

### UIMA annotator

As a UIMA annotator: the annotator is `com.as.text_understanding.uima_annotators.pasta.FromDkproPastaAnnotator`. It assumes the document is already annotated by some DKPro annotations, as specified in `FromDkproPastaAnnotator`'s `TypeCapability` annotation. To get this annotations, the precondition annotators, specified in `DemoPasta.PRECONDITION_ANNOTATORS` suffice. (Note that these precondition annotators can be replaced by other DKpro annotators, like, for example, Stanford-Parser).

As for the results, **please note** that the `Predicate` annotation mostly covers also some arguments. The actual predicate can be retrieved by the field `verb` of `Predicate`.

An example program which runs PASTA as a UIMA annotator, from within UIMA's *Document-Analyzer*, is provided at `com.as.text_understanding.uima_annotators.pasta.DemoPastaAnnotator`.
 




