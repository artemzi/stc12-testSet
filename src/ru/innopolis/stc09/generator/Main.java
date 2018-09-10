package ru.innopolis.stc09.generator;

public class Main {
    public static void main(String[] args) {
        Generator myGenerator = new Generator();
        myGenerator.generate("D://temp//testSet", 1_000_000_000, 2_000_000_000, 3, "starter", 3000);
        myGenerator.generate("D://temp//testSet", 1_500_000, 5_000_000, 100, "starter", 3000);
        myGenerator.generate("D://temp//testSet", 5_000, 30_000, 1000, "starter", 3000);
        myGenerator.generate("D://temp//testSet", 5_000, 30_000, 1000, "smarter", 3000);
        //myGenerator.generate("D://temp//testSet", 5_000, 5_001, 1, "smarter", 3000);
    }
}
