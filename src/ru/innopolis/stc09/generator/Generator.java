package ru.innopolis.stc09.generator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class Generator {
    Random rnd = new Random(System.currentTimeMillis());

    public void generate(String path, int minSize, int maxSize, int amount, String popularWord, int popularWordProbability) {
        for (int i = 0; i < amount; i++) {
            generateSingleFile(path, minSize, maxSize, popularWord, popularWordProbability);
            System.out.println("Generated " + i + " of " + amount);
        }
    }

    private void generateSingleFile(String path, int minSize, int maxSize, String popularWord, int popularWordProbability) {
        int thisSize = minSize + rnd.nextInt(maxSize - minSize);
        String fileName = path + "/" + UUID.randomUUID() + ".txt";
        createAndFillFile(fileName, thisSize, popularWord, popularWordProbability);
    }

    private void createAndFillFile(String fileName, int thisSize, String popularWord, int popularWordProbability) {
        try (FileOutputStream out = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            int counter = 0;
            int counter2 = 0;
            while (!fileIsBigEnough(fileName, thisSize, false)) {
                // перевод строки в байты
                counter++;
                counter2++;
                if ((counter > 100_000) && (thisSize > 100_000_000)) {
                    counter = 0;
                    fileIsBigEnough(fileName, thisSize, true);
                }
                String newSentence = makeNewSentence(popularWord, popularWordProbability);
                if (counter2 > 10) {
                    counter2 = 0;
                    newSentence = newSentence + "\r\n";
                }
                byte[] buffer = newSentence.getBytes();
                bos.write(buffer, 0, buffer.length);
                bos.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean fileIsBigEnough(String file, int size, boolean log) {
        File f = new File(file);
        long len = f.length();
        if (log) {
            System.out.println("Size: " + len + " of " + size);
        }
        return len < size ? false : true;
    }

    private String makeNewSentence(String word, int probability) {
        StringBuffer newString = new StringBuffer(generateWord(word, probability, true));
        Random rnd = new Random();
        int size = rnd.nextInt(10);
        for (int i = 0; i < (size - 1); i++) {
            newString.append(" ");
            newString.append(generateWord(word, probability, false));
        }
        newString.append(". ");
        return newString.toString();
    }

    private StringBuffer generateWord(String word, int probability, boolean first) {
        if ((rnd.nextInt(probability) == 1) && (!first)) {
            return new StringBuffer(word);
        }
        StringBuffer out = new StringBuffer();
        out.append(newLetter(first));
        int size = rnd.nextInt(10);
        for (int i = 0; i < size - 1; i++) {
            out.append(newLetter(false));
        }
        return out;
    }

    private char newLetter(boolean upper) {
        int code = rnd.nextInt(26);
        code += upper ? 65 : 97;
        return (char) code;
    }
}
