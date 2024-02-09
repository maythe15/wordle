import java.util.HashMap;

public class HiddenWord{
    String word;
    public HiddenWord(String word){
        this.word=word;
    }
    public int[] getHint(String guess){
        int[] states = new int[this.word.length()];
        HashMap<String, Integer> word_charcount=new HashMap<>();
        for (int i=0; i<this.word.length(); i++){
            states[i]=0;
            String w_char=word.substring(i,i+1);
            String g_char=guess.substring(i,i+1);
            if (w_char.equals(g_char)){
                states[i]=2;
            } else {
                if (word_charcount.containsKey(w_char)){
                    word_charcount.put(w_char, word_charcount.get(w_char)+1);
                } else {
                    word_charcount.put(w_char, 1);
                }
            }
        }

        for (int i=0; i<this.word.length(); i++){
            if (!(states[i]==2)){
                String g_char=guess.substring(i,i+1);
                if (word_charcount.containsKey(g_char)&&word_charcount.get(g_char)>0){
                    states[i]=1;
                    word_charcount.put(g_char, word_charcount.get(g_char)-1);
                }
            }
        }
        return states;
    }
}