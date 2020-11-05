package lexicalAnalyzer;


import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

class SortByIndex implements Comparator<Match>
{
    public int compare(Match a, Match b)
    {
        return a.start - b.start;
    }

}

public class Analyzer {
    Token[] Tokens = {new Token("ERROR","\\b[0-9]+[A-Za-z_0-9]+\\b"),
            new Token("STRING_LITERAL","\".*?\""),
            new Token("SINGLE_COMMENT","\\/\\/.*?\\s*.*?\n"),
            new Token("MULTI_COMMENT","\\/\\*.*?\\s*.*?\\*\\/"),
            new Token("AUTO","\\bauto\\b"),
            new Token("NEW","\\bnew\\b"),
            new Token("EOF","0\\z"),
            new Token("TRUE","\\btrue\\b"),
            new Token("FALSE","\\bfalse\\b"),
            new Token("BREAK","\\bbreak\\b"),
            new Token("BOOL","\\bbool\\b"),
            new Token("CASE","\\bcase\\b"),
            new Token("CHAR","\\bchar\\b"),
            new Token("CONST","\\bconst\\b"),
            new Token("continue","\\bcontinue\\b"),
            new Token("DEFAULT ","\\bdefault\\b"),
            new Token("DO","\\bdo\\b"),
            new Token("DOUBLE","\\bdouble\\b"),
            new Token("ELSE","\\belse\\b"),
            new Token("ENUM","\\benum\\b"),
            new Token("EXTERN","\\bextern \\b"),
            new Token("FLOAT","\\bfloat\\b"),
            new Token("FOR","\\bfor\\b"),
            new Token("GOTO","\\bgoto\\b"),
            new Token("IF","\\bif\\b"),
            new Token("INT","\\bint\\b"),
            new Token("LONG","\\blong\\b"),
            new Token("REGISTER","\\bregister\\b"),
            new Token("RETURN","\\breturn\\b"),
            new Token("SHORT","\\bshort\\b"),
            new Token("SIGNED","\\bsigned\\b"),
            new Token("SIZEOF","\\bsizeof\\b"),
            new Token("STATIC","\\bstatic\\b"),
            new Token("STRUCT","\\bstruct\\b"),
            new Token("SWITCH","\\bswitch\\b"),
            new Token("TYPEDEF","\\btypedef\\b"),
            new Token("UNION","\\bunion\\b"),
            new Token("UNSIGNED ","\\bunsigned\\b"),
            new Token("VOID","\\bvoid\\b"),
            new Token("VOLATILE","\\bvolatile\\b"),
            new Token("WHILE","\\bwhile\\b"),
            new Token("FLOAT_LITERAL","[-+]?\\d+\\.\\d+"),
            new Token("INTEGRAL_LITERAL","[-+]?\\d+"),
            new Token("CHAR_LITERAL","'\\w'"),
            new Token("LEFT_CURLY_B","\\}"),
            new Token("RIGHT_CURLY_B","\\{"),
            new Token("LEFT_SQUARE_B","\\]"),
            new Token("RIGHT_SQUARE_B","\\["),
            new Token("LEFT_ROUND_B","\\)"),
            new Token("RIGHT_ROUND_B","\\("),
            new Token("COMMA","\\,"),
            new Token("SEMICOLON","\\;"),
            new Token("DOT ","\\."),
            new Token("PREPROCESSOR","\\#"),
            new Token("BACKWARD_SLASH","\\\\"),
            new Token("MINUS","\\-"),
            new Token("PLUS","\\+"),
            new Token("ASTERICK","\\*"),
            new Token("DIVIDE","\\/"),
            new Token("MOD","\\%"),
            new Token("LESS_EQ","\\=\\>"),
            new Token("GREAT_EQ","\\=\\<"),
            new Token("EQUAL","\\=\\="),
            new Token("NOT_EQUAL","\\!\\="),
            new Token("LEFT_SHIFT","\\>\\>"),
            new Token("RIGHT_SHIFT","\\<\\<"),
            new Token("LESSTHAN","\\<"),
            new Token("GREATERTHAN","\\>"),
            new Token("ASSIGN_OPERATOR","\\="),
            new Token("NOT ","\\!"),
            new Token("AND","\\&\\&"),
            new Token("OR","\\|\\|"),
            new Token("BITWISE_AND","\\&"),
            new Token("BITWISE_OR","\\|"),
            new Token("BITWISE_XOR","\\^"),
            new Token("BITWISE_NOT","\\~"),
            new Token("ID","\\b[A-Za-z_]*[0-9]*\\b")
            };

    public Analyzer() {}

    public void swap (Match x,Match y){
        Match temp = new Match(new Token(x.getTok().getType(),x.getTok().getValue()),x.getStart(),x.getEnd());
        x = new Match(new Token(y.getTok().getType(),y.getTok().getValue()),y.getStart(),y.getEnd());
        y = new Match(new Token(temp.getTok().getType(),temp.getTok().getValue()),temp.getStart(),temp.getEnd());
    }

    public void sort(ArrayList<Match> matches){
        for (int i=0 ; i<matches.size() ; i++){
            for (int j=i+1 ; j<matches.size() ; j++){
                if (matches.get(i).getStart()>matches.get(j).getStart())
                    swap(matches.get(i),matches.get(j));
            }
        }
    }

    public ArrayList<Match> regexChecker(String str) {


        ArrayList<Match> matches = new ArrayList<>();
        for (int i = 0; i<Tokens.length ; i++) {

            Pattern checkRegex = Pattern.compile(Tokens[i].getValue());

            Matcher regexMatcher = checkRegex.matcher(str);

            while (regexMatcher.find()) {
                if (regexMatcher.group().length() != 0) {
                    boolean flage = true;
                    if (matches.size() > 0) {
                        for (Match x : matches) {
                            if (regexMatcher.start() == x.start || (regexMatcher.start() >= x.start && regexMatcher.start() < x.end))
                                flage = false;
                        }
                        if (flage)
                            matches.add(new Match(new Token(Tokens[i].getType(), regexMatcher.group()), regexMatcher.start(), regexMatcher.end()));
                    } else {
                        matches.add(new Match(new Token(Tokens[i].getType(), regexMatcher.group()), regexMatcher.start(), regexMatcher.end()));
                    }
                }
            }
        }
        Collections.sort(matches, new SortByIndex());

        /*for (Match x : matches)
            System.out.println("<" + x.getTok().getType() + "> : " + x.getTok().value + " , Start : " + x.start+" , End : " + x.end);*/

        return matches ;
    }

    public boolean isWhiteSpace(String str){

        Pattern checkRegex = Pattern.compile("\\s+");

        Matcher regexMatcher = checkRegex.matcher(str);
        while (regexMatcher.find()) {
            if (regexMatcher.group().length() == str.length()) {
                return true;
            }
        }
        return false ;
    }

    public void isValid(String str) throws IOException {

        ArrayList<Match> matches = regexChecker(str);

        File file = new File("F:\\Courses Materials\\Andriod Course\\Lexical Analyzer\\src\\lexicalAnalyzer\\Output.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i=0 ; i<matches.size() ; i++) {
            if (matches.get(i).getTok().getType() == "ERROR") {
                bw.write("error :");
                bw.newLine();
                bw.write(matches.get(i).getTok().getValue()+" ==>  no ID starts with number");
                bw.newLine();
                bw.close();
                System.out.println("error : \n" + matches.get(i).getTok().getValue()+" ==>  no ID starts with number");
                return;
            }
            String temp = " ";
            if ((i==0)&&(matches.get(0).start>0)){
                for (int j=0 ; j<matches.get(0).start ; j++)
                    temp += str.charAt(j);

            } else if (i==matches.size()-1){
                if ((str.length()-matches.get(i).end) >= 1){
                    for (int j=matches.get(i).getEnd() ; j<str.length() ; j++)
                        temp += str.charAt(j);
                }
            }else {
                if ((matches.get(i+1).start-matches.get(i).end) >= 1){
                    for (int j=matches.get(i).end ; j<matches.get(i+1).start ; j++)
                        temp += str.charAt(j);
            }
            }
            //System.out.println(temp);
            if(!(isWhiteSpace(temp))){
                bw.write("error : ");
                bw.newLine();
                bw.write(temp.trim()+" ==> didn't match any token");
                bw.newLine();
                bw.close();
                System.out.println("error : \n"+temp.trim()+" ==> didn't match any token");
                return;
            }
        }


        for (Match x : matches) {
            bw.write("<" + x.getTok().getType() + "> : " + x.getTok().value);
            bw.newLine();
        }
        bw.close();
        for (Match x : matches)
            System.out.println("<" + x.getTok().getType() + "> : " + x.getTok().value + " , Start : " + x.start+" , End : " + x.end);
    }

}
