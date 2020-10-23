package dictionary;

public class Word {
    private String spelling;
    private String pronunciation;
    private String meaning;
    private boolean fav;

    /**
     * constructors.
     */
    public Word() {
    }

    public Word(String spelling, String meaning) {
        this.spelling = spelling;
        this.meaning = meaning;
        fav = false;
    }

    public Word(String spelling, String pronunciation, String meaning) {
        this.spelling = spelling;
        this.pronunciation = pronunciation;
        this.meaning = meaning;
        fav = false;
    }

    public Word(Word other) {
        this.spelling = other.getSpelling();
        this.pronunciation = other.getPronunciation();
        this.meaning = other.getMeaning();
        this.fav = other.isFav();
    }

    /**
     * getters and setters.
     */

    public String getSpelling() {
        return spelling;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getMeaning() {
        return meaning;
    }

    public boolean isFav() {
        return fav;
    }

    public void setSpelling(String name) {
        this.spelling = spelling;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    /**
     * @param other is an input String
     * @return true if input String is prefix of wordSpelling
     */
    public boolean hasPrefix(String other) {

        if (other.length() > this.getSpelling().length()) {
            return false;
        }

        return this.getSpelling().startsWith(other);
    }


    /**
     * check if 2 words are the same.
     * @param other word input.
     * @return true if 2 words are the same.
     */
    public boolean equalsTo(Word other) {
        return (spelling == other.getSpelling()
                && pronunciation == other.getPronunciation()
                && meaning == other.getMeaning());
    }

    /**
     * check if a word is empty (doesn't have spelling or meaning)
     */
    public boolean isEmpty() {
        return (spelling == null || meaning == null || spelling.length() < 1 || meaning.length() < 1);
    }

    /**
     * @return string of word (spelling, pronounce, meaning).
     */
    public String toString() {
        if (this.isEmpty()) {
            return "*--This word is empty!--*";
        } else {
            return "Word: " + getSpelling()
                    + "\n\t+ " + getPronunciation()
                    + "\n\t+ " + getMeaning();
        }
    }

    /**
     * print out a word.
     */
    public void print() {
        System.out.print(toString());
    }
}
