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
     * load data from a file txt.
     */
    public void dictionaryLoadFromFile(String pathFile) {

        try {
            InputStream is = Dictionary.class.getResourceAsStream(pathFile);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String inputLine = reader.readLine();

            while (inputLine != null) {
                // Split the input line.
                String[] words = inputLine.split("\t");

                // Ignore empty lines.
                if (inputLine.equals(""))
                    continue;

                if (words.length > 2) {
                    Word newWord = new Word(words[0], words[1] , words[2]);
                    addWord(newWord);

                } else if (words.length == 2) {
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
    public void dictionaryLoadFromFile() {
        this.dictionaryLoadFromFile("/data/words.txt");
    }


    /**
     * write data to a file.
     */
    public void dictionarySaveToFile() {
        try {
            URL resourceUrl = getClass().getResource("/data/backUp.txt");

            String path = resourceUrl.getPath();

            OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(new File(path)), StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(os);
            /*ArrayList<Word> words = this.getDictionaryListWords();
            for (Word word : words) {
                writer.write(word.getSpelling() + "\t");
                writer.write(word.getPronunciation() + "\t");
                writer.write(word.getMeaning() + "\n");
            }*/
            writer.write("helloo");
            writer.newLine();
            writer.append("hello");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
