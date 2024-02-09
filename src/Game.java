import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Game {
    private Scanner scan;
    private Keyprinter keyprint;
    private ArrayList<String> guesslist;
    private String red="\033[0;31m";
    private String green="\033[0;32m";
    private String yellow="\033[0;33m";
    private String reset="\033[0m";

    public Game() {
        this.keyprint = new Keyprinter(new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"});
        this.scan = new Scanner(System.in);
        this.guesslist = new ArrayList<>();
    }

    public void run() throws IOException {
        InputStream in = getClass().getResourceAsStream("res/words.txt");
        assert in != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Scanner scan = new Scanner(reader);
        ArrayList<String> words = new ArrayList<>();
        while (scan.hasNextLine()){
            words.add(scan.nextLine());
        }
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()-1));
        HiddenWord hword = new HiddenWord(word);
        Writer.write("");
        clear();
        ArrayList<String> defs = Definition.getDefinition(hword.word);
        String guess=getGuess();
        Writer.write("");
        clear();
        int[] state=new int[] {0,0,0,0,0};
        int guesses=1;
        while (!isCorrect(state)){
            System.out.println("Guess "+((int)guesses+1));
            StringBuilder os= new StringBuilder();
            state=hword.getHint(guess);
            keyprint.updateStrings(guess, state);
            int sindex=0;
            os.append("  ");
            for (int i: state){
                os.append(i == 0 ? red : (i == 1 ? yellow : green));
                os.append(guess.charAt(sindex));
                sindex++;
            }
            os.append(reset);
            guesslist.add(os.toString());
            if (isCorrect(state)){
                break;
            }
            Writer.write("Letters: "+keyprint.getKeys());
            System.out.println("Guesses:");
            for (String i: this.guesslist){
                Writer.write(i);
            }
            guess=getGuess();
            clear();
            guesses++;
        }
        clear();
        System.out.println("You got the answer in "+guesses+" guesses\nYour guesses:");
        for (String i: this.guesslist){
            Writer.write(i);
        }
        if (!defs.isEmpty()) {
            System.out.println("Definition:");
            for (String i : defs) {
                System.out.println("\t" + i);
            }
        }
        System.out.println(reset);
    }
    public boolean isCorrect(int[] state){
        for (int i:state){
            if (i!=2){
                return false;
            }
        }
        return true;
    }
    public String getGuess(){
        int lgs=0;
        String str="";
        while (lgs!=5) {
            System.out.print(">>");
            str=this.scan.nextLine();
            lgs=str.length();
        }
        return str.toLowerCase();
    }

    static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}