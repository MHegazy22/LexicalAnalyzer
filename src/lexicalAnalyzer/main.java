package lexicalAnalyzer;
//Name : Mohamed Shaaban
//ID   : 20160209

// Name : Mohamed Adbelmoniem
//ID   : 20160214

//Name : Mahmoud Mohamed
//ID   : 20160232

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {

        String str = Read("F:\\Courses Materials\\Andriod Course\\Lexical Analyzer\\src\\lexicalAnalyzer\\input.txt");

        //System.out.println(str);
        Analyzer x = new Analyzer();

        //x.regexChecker(str);
        x.isValid(str);
    }
    public static String Read(String path) throws FileNotFoundException, IOException {
        String data = "";
        FileReader fr = new FileReader(path);
        int i;
        while ((i = fr.read()) != -1) {
            data += (char) i;
        }
        return data;
    }



}
