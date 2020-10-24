package dictionary;


import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Dictionary extends DictionaryManagement {


    /**
     * Load data from a txt file has path pathFile.
     * This is English - Vietnamese.
     * @param pathFile lead to file location in resources directory.
     */
    public void dictionaryLoadFromFile(String pathFile) {

        try {
            clearDictionary();

            InputStream is = Dictionary.class.getResourceAsStream(pathFile);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String inputLine = reader.readLine();

            System.out.println("Start load EV file.");

            while (inputLine != null) {
                // Split the input line.
                String[] words = inputLine.split("\t");

                // Ignore empty lines.
                if (inputLine.equals(""))
                    continue;

                if (words.length > 2) {
                    Word newWord = new Word(words[0], words[1] , words[2]);
                    addWord(newWord);

                } else
                if (words.length == 2) {
                    Word newWord = new Word(words[0], words[1]);
                    addWord(newWord);
                }

                inputLine = reader.readLine();
            }
            reader.close();

        } catch (IOException exception) {
            System.out.println("Could not find file.");
        }
    }

    /**
     * Load data from a default txt file.
     * This is English - Vietnamese.
     */
    public void dictionaryLoadFromFile() {
        this.dictionaryLoadFromFile("/data/words.txt");
    }


    /**
     * Load data from a txt file has path pathFile.
     * This is Vietnamese - English.
     * @param pathFile lead to file location in resources directory.
     */
    public void dictionaryVELoadFromFile(String pathFile) {
        try {
            clearDictionary();

            System.out.println("Start load VE file.");

            InputStream is = Dictionary.class.getResourceAsStream(pathFile);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String inputLine = reader.readLine();

            while (inputLine != null) {
                // Split the input line.
                String[] words = inputLine.split(" : ");

                // Ignore empty lines.
                if (inputLine.equals(""))
                    continue;

                if (words.length == 2) {
                    Word newWord = new Word(words[0], words[1]);
                    addWord(newWord);
                }

                inputLine = reader.readLine();
            }
            reader.close();

        } catch (IOException exception) {
            System.out.println("Could not find file.");
        }
    }

    /**
     * Load data from a default txt file.
     * This is Vietnamese - English.
     */
    public void dictionaryVELoadFromFile() {
        this.dictionaryVELoadFromFile("/data/vnedict.txt");
    }


    /**
     * Write data to a txt file has path pathFile (back up).
     * This is English - Vietnamese.
     * @param pathFile lead to file location in resources directory.
     */
    public void dictionarySaveToFile(String pathFile) {
        try {
            URL resourceUrl = getClass().getResource(pathFile);

            String path = resourceUrl.getPath();

            OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(new File(path)), StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(os);
            ArrayList<Word> words = this.getDictionaryListWords();
            for (Word word : words) {
                writer.write(word.getSpelling() + "\t");
                writer.write(word.getPronunciation() + "\t");
                writer.write(word.getMeaning() + "\n");
            }
            writer.flush();
            writer.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write data to a default txt file(back up).
     * This is English - Vietnamese.
     */
    public void dictionarySaveToFile() {
        this.dictionarySaveToFile("/data/backUp.txt");
    }

    /**
     * Write data to a txt file has path pathFile (back up).
     * This is Vietnamese - English.
     * @param pathFile lead to file location in resources directory.
     */
    public void dictionaryVESaveToFile(String pathFile) {
        try {
            URL resourceUrl = getClass().getResource(pathFile);

            String path = resourceUrl.getPath();

            FileOutputStream fos = new FileOutputStream(path);
            OutputStreamWriter os = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(os);

            ArrayList<Word> words = this.getDictionaryListWords();
            for (Word word : words) {
                writer.write(word.getSpelling() + " : ");
                writer.write(word.getMeaning() + "\n");
            }

            writer.flush();

            writer.close();
            os.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write data to a default txt file(back up).
     * This is Vietnamese - English.
     */
    public void dictionaryVESaveToFile() {
        this.dictionaryVESaveToFile("/data/vnedictBU.txt");
    }
}
