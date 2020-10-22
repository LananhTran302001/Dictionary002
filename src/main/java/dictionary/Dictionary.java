package dictionary;

import java.io.*;

public class Dictionary {
    public DictionaryManagement dictionary = new DictionaryManagement();


    /**
     * load data from a file txt.
     */
    public void dictionaryLoadFromFile() {

        try {
            String pathFile = "/data/words.txt";
            InputStream is = Dictionary.class.getResourceAsStream(pathFile);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String inputLine = reader.readLine();

            while (inputLine != null) {
                // Split the input line.
                String[] words = inputLine.split("\t");

                // Ignore empty lines.
                if (inputLine.equals(""))
                    continue;

                Word newWord = new Word(words[0], words[1] , words[2]);
                dictionary.addWord(newWord);

                inputLine = reader.readLine();
            }
            reader.close();

        } catch (IOException exception) {
            System.out.println("Could not find file.");
        }
    }


    /**
     * write data to a file.
     * @param pathFile
     */
    public void dictionarySaveToFile(String pathFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(pathFile)));

            for (Word word : dictionary.getDictionaryListWords()) {
                writer.write(word.getSpelling() + "\t");
                writer.write(word.getPronunciation() + "\t");
                writer.write(word.getMeaning() + "\n");
            }

            writer.flush();
            writer.close();
        }
        catch (IOException exception) {
            System.out.println("An error occur.");
        }
    }

    public void dictionarySaveToFile() {
        this.dictionarySaveToFile("/data/exportFile.txt");
    }

}
