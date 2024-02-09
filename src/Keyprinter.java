import java.util.ArrayList;
import java.util.HashMap;

public class Keyprinter {
    HashMap<String, Integer> keystates;
    ArrayList<String> keys;
    public Keyprinter(String[] keys){
        this.keystates=new HashMap<>();
        this.keys=new ArrayList<>();
        for (String i:keys){
            if (!this.keys.contains(i)){
                this.keys.add(i);
                this.keystates.put(i, 0);
            }
        }
    }
    public String getKeys(){
        String green="\033[0;32m";
        String yellow="\033[0;33m";
        String reset="\033[0m";
        StringBuilder out= new StringBuilder();
        for (String i: this.keys){
            switch (this.keystates.get(i)){
                case 0:
                    out.append(reset).append(i);
                    break;
                case 2:
                    out.append(yellow).append(i);
                    break;
                case 3:
                    out.append(green).append(i);
                    break;
                default:
                    break;
            }
        }
        return out+reset;
    }

    public void updateStrings(String word, int[] states){
        int sindex=0;
        for (int i: states){
            String c=word.substring(sindex, sindex+1);
            switch (i){
                case 0:
                    this.keystates.put(c, Math.max(this.keystates.get(c),1));
                    break;
                case 2:
                    this.keystates.put(c, Math.max(this.keystates.get(c),3));
                    break;
                case 1:
                    this.keystates.put(c, Math.max(this.keystates.get(c),2));
                    break;
            }
            sindex++;
        }
    }
}
