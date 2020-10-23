package dictionary;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DictionaryManagement {

    private ArrayList<Word> listWords = new ArrayList<Word>();

    public DictionaryManagement() {
        ArrayList<Word> listWords = new ArrayList<Word>();
    }

    /**
     * getter.
     * @return list of words in dictionary.
     */
    public ArrayList<Word> getDictionaryListWords() {
        return listWords;
    }

    /**
     * @param wordSpelling is an input string.
     * @return a Word has spelling = wordSpelling.
     */
    public Word searchWord(String wordSpelling) {
        try {
            int index = this.findIndex(wordSpelling);
            return this.getWordAt(index);
        } catch (NullPointerException exception) {
            System.out.println("Haven't received any string to search.");
            return null;
        }
    }

    public Word searchWord(Word word) {
        try {
            return this.searchWord(word.getSpelling());
        } catch (NullPointerException exception) {
            System.out.println("Haven't received any string to search.");
            return null;
        }

    }


    /**
     * @param wordSpelling is an input string.
     * @return an arraylist of words can be result of searchWord.
     */
    public ArrayList<String> searchHintSpelling(String wordSpelling) {
        try {
            ArrayList<String> hintWords = new ArrayList<String>();

            int listLength = listWords.size();
            int index = this.findIndexHint(wordSpelling);
            if (index >= listLength || index < 0) { return null; }

            // Using getWordAt to avoiding outOfBounder case.
            Word currentWord = this.getWordAt(index);

            while (index < listLength && currentWord.hasPrefix(wordSpelling)) {
                hintWords.add(currentWord.getSpelling());

                index++;
                // if not using getWordAt
                // When index = listLength
                // currentWord = listWords.get(index) -> outOfRange
                currentWord = this.getWordAt(index);
            }
            return hintWords;

        } catch (NullPointerException exception) {
            System.out.println("Haven't received any string to search hint.");
            return null;
        }
    }



    /**
     * add a Word word to list of words.
     * @param newWord is an input Word.
     */
    public void addWord(Word newWord) {
        try {
            if (!newWord.isEmpty()) {

                int indexInsert = this.findIndexInsert(newWord.getSpelling());

                System.out.println("Insert word at " + indexInsert);
                if (indexInsert >= 0 && indexInsert <= listWords.size()) {
                    listWords.add(indexInsert, newWord);
                }
            }
        } catch (NullPointerException exception) {
            System.out.println("Dictionary management hasn't received any word to add.");
        }
    }

    /**
     * Say if word can be added , add word.
     * @param newWord is an input word.
     * @return true if we can add this word to the dictionary.
     */
    public boolean canAddWord(Word newWord) {
        try {
            if (!newWord.isEmpty()) {

                int indexInsert = this.findIndexInsert(newWord.getSpelling());

                System.out.println("Insert word at " + indexInsert);
                if (indexInsert >= 0 && indexInsert <= listWords.size()) {
                    listWords.add(indexInsert, newWord);
                    return true;
                }
            }
            return false;

        } catch (NullPointerException exception) {
            System.out.println("Dictionary management hasn't received any word to add.");
            return false;
        }
    }

    /**
     * delete a word has spelling = wordSpelling in list of words.
     * @param wordSpelling is an input String.
     */
    public void deleteWord(String wordSpelling) {
        try {
            int index = this.findIndex(wordSpelling);
            if (index >= 0 && index <= listWords.size()) {
                listWords.remove(index);
            }
        } catch (NullPointerException exception) {
            System.out.println("Haven't received any word to delete.");
        }
    }

    /**
     * delete a Word word in list of words.
     * @param word is an input Word.
     */
    public void deleteWord(Word word) {
        try {
            this.deleteWord(word.getSpelling());
        } catch (NullPointerException exception) {
            System.out.println("Haven't received any word to delete.");
        }

    }


    /**
     * update a Word in list words.
     * @param wordSpelling is spelling of old word.
     * @param newWord is new Word word .
     */
    public void upgradeWord(String wordSpelling, Word newWord) {
        try {
            this.deleteWord(wordSpelling);
            this.addWord(newWord);
        } catch (NullPointerException exception) {
            System.out.println("Your word is null, can not upgrade.");
        }
    }



    /**
     * @param wordSpelling is an input String.
     * @return index of word has spelling = wordSpelling in the list.
     */
    private int findIndex(String wordSpelling) {
        if (wordSpelling == null) {
            return -1;
        }

        // binary search
        int lower = 0;
        int upper = listWords.size() - 1;
        int mid;

        while (upper >= lower) {
            mid = lower + (upper - lower) / 2;

            int isFront = listWords.get(mid).getSpelling().compareTo(wordSpelling);
            // if isFront = 0 -> word being searched = listWords.get(mid);
            //            > 0 -> word is front of listWords.get(mid)
            //            < 0 -> word is behind listWords.get(mid)


            if (isFront == 0) {
                return mid;
            } else if (isFront > 0) {
                upper = mid - 1;
            } else {
                lower = mid + 1;
            }
        }
        return -1;
    }

    /**
     * @param wordSpelling is an input String.
     * @return index to insert that word to the list.
     */
    private int findIndexInsert(String wordSpelling) {
        if (wordSpelling == null) {
            return -1;
        }

        if (listWords == null) {
            return 0;
        }
        // binary search
        // find index where new word will be inserted
        int lower = 0;
        int upper = listWords.size() - 1;
        int mid;

        while (upper >= lower) {
            mid = lower + (upper - lower) / 2;

            int isFront = listWords.get(mid).getSpelling().compareTo(wordSpelling);
            // if isFront = 0 -> word being inserted = listWords.get(mid);
            //            > 0 -> word will be front of listWords.get(mid)
            //            < 0 -> word will  be behind listWords.get(mid)


            if (isFront == 0) {
                return -1;
            } else if (isFront > 0) {
                upper = mid - 1;
            } else {
                lower = mid + 1;
            }
        }
        if (upper == -1) {
            return 0;
        }
        return lower;
    }

    /**
     * hintWords are Words have prefix = input string wordSpelling.
     * @param wordSpelling is an input String.
     * @return index of first hintWord in the list.
     */
    private int findIndexHint(String wordSpelling) {
        if (wordSpelling == null) {
            return -1;
        }
        // binary search
        // find index of the first hintWord
        int lower = 0;
        int upper = listWords.size() - 1;
        int mid;

        while (upper >= lower) {
            mid = lower + (upper - lower) / 2;

            // must sure 0 <= mid < listWordsSize
            int isFront = listWords.get(mid).getSpelling().compareTo(wordSpelling);
            // if isFront = 0 -> word being inserted = listWords.get(mid);
            //            > 0 -> word will be front of listWords.get(mid)
            //            < 0 -> word will  be behind listWords.get(mid)


            if (isFront == 0) {
                return mid;
            } else if (isFront > 0) {
                upper = mid - 1;
            } else {
                lower = mid + 1;
            }
        }
        return lower;
    }

    public Word getWordAt(int index) {
        if (index >= 0 && index < listWords.size()) {
            return listWords.get(index);
        }
        return null;
    }

    public int getDictionarySize() {
        return listWords.size();
    }

    public void printDictionary() {
        for (Word word : listWords) {
            System.out.println("---------------------------------------------");
            System.out.println(word.getSpelling());
            System.out.println(word.getPronunciation());
            System.out.println(word.getMeaning());
        }
    }


}

