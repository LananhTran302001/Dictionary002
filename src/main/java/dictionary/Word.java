package dictionary;

public class Word {
    private String spelling;
    private String pronunciation;
    private String meaning;

    /**
     * constructors.
     */
    public Word() {
    }

    public Word(String spelling, String meaning) {
        this.spelling = spelling;
        this.meaning = meaning;
    }

    public Word(String spelling, String pronunciation, String meaning) {
        this.spelling = spelling;
        this.pronunciation = pronunciation;
        this.meaning = meaning;
    }

    public Word(Word other) {
        this.spelling = other.getSpelling();
        this.pronunciation = other.getPronunciation();
        this.meaning = other.getMeaning();
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

    public void setSpelling(String name) {
        this.spelling = spelling;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
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
     * check if a word is empty (doesn't have spelling or meaning)
     */
    public boolean isEmpty() {
        return (spelling == null || meaning == null);
    }

    /**
     * print out a word.
     */
    public void print() {
        if (this.isEmpty()) {
            System.out.println("*--This word is empty--*!");
        }
        System.out.print("Word: ");
        System.out.println(spelling);
        System.out.println("\t+ " + pronunciation);
        System.out.println("\t+ " +meaning);
    }
}
