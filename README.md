# Synonyms
<b>Background</b></br>
One type of question encountered in the SAT and the Test of English as a Foreign Language (TOEFL) is the "Synonym Question". In questions like this, students are asked to pick a synonym of a word from a list of alternatives. For example:
1. Vexed</br>
a. synonym</br>b. annoyed</br>c. book</br>d. spellbound</br>

This program can learn to answer questions like the one shown above. In order to do that, the program will approximate the semantic similarity of any pair of words. The semantic similarity between two words is the measure of the closeness of their meanings. In order to answer the question above, you will compute the semantic similarity between the word (vexed) and each of the possible answers, and pick the answer with the highest semantic similarity.

<b>Semantic Similarity</b></br>
Given a word <i>w</i> and a list of potential synonyms <i>s<sub>1</sub>, s<sub>2</sub>, s<sub>3</sub>, s<sub>4</sub></i>, we compute the similarities of <i>(w, s<sub>1</sub>), (w, s<sub>2</sub>), (w, s<sub>3</sub>), (w, s<sub>4</sub>)</i> and choose the word whose similarity to <i>w</i> is the highest. We will measure the semantic similarity of pairs of words by first computing a semanticdescriptor vector for each of the words. We will then measure the similarity by using the cosine similarity between the two vectors. Given a text with <i>n</i> words denoted by <i>(w<sub>1</sub>, w<sub>2</sub>, ..., w<sub>n</sub>)</i> and a word <i>w</i>, let <i>desc<sub>w</sub></i> be the semantic descriptor vector of <i>w</i> computed using the text. <i>desc<sub>w</sub></i> is an n-dimensional vector where the i-th coordinate of <i>desc<sub>w</sub></i> is the number of sentences in which both <i>w</i> and <i>w<sub>i</sub></i> occur.

<b>A HashMap Implementation</b></br>
A handy implementation of the semantic descriptor vector is to use a HashMap.The key in theHashMap is the word under consideration. This would be the word “Vexed” in the example above. The value of the HashMap is another HashMap, where the keys are words and values are counts.

<b>Cosine Similarity</b></br>
Researchers have suggested many ways of measuring semantic similarity. We will use a fairly straightforward method known as the cosine similarity. We cannot apply the formula directly to our semantic descriptors since we do not store the entries which are equal to zero. However, we can still compute the cosine similarity between vectors by only considering the positive entries.

<b>Corpus</b></br>
In linguistics (which is what we are doing here), a corpus (plural corpora) or text corpus is a large and structured set of texts that are used to do statistical analysis and testing, checking occurrences or validating linguistic rules within a specific language. The large set of text give us enough data to do real statistical analysis such as cosine similarity. We will define and use a corpus to test and run our synonym programs.
