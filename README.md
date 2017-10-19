# Indexer
Create a Retrieval API that enables performing query retrieval on your index

The dataset is the scenes from the complete works of Shakespeare. This data has been tokenized and is made available as shakespeare-scenes.json 

This dataset has been preprocessed by stripping out punctuation and stemmed using the Krovetz Stemmer. No stopwords have been removed.

Indexer performs the following:

1.Reads the tokenized and stemmed document collection provided in the assignment.
2.Term vectors are constructed by splitting the given text by spaces (the regex \\s+), and ignoring blank strings.
3.A simple inverted index is built with positional information.  
4.Command line parameter is used to choose between compressed and uncompressed inverted lists. Compressed lists should use both delta encoding and vbyte compression.
5.Inverted lists and all ancillary index structures are  written to disk, enabling reopening a written index. 
6.An appropriate API is added to  index so as to enable accessing the vocabulary, term counts, document counts and other statistics that  will be required to perform the evaluation activities.
7.Retrieval API is provided that enables performing query retrieval on  index. For this project, a raw count model is sufficient.

*Evaluation*
Using your indexer, two types of index are constructed , one using compression, the second without compression.

1. Vocabulary of the two indexes (terms and counts)  are compared to ensure they are identical.
2. 7 terms are selected randomly from the vocabular.  The selected terms, their term frequency and document frequency are recorded. This is repeated 100 times.
3.Using Dice's coefficient, the highest scoring two word phrase for each of the 7 terms in your set of 100 are identified. 

4. Retrieval API  takes these 100 sets of 7 terms as  queries, performs a timing experiment to examine the compression hypothesis.  The same experiment is repeated using the 100 sets  of 7 two word phrases.

