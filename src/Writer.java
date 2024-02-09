import java.io.IOException;

public class Writer {
    public static void write(String string){
        String osname=getOs();
        if (osname.equalsIgnoreCase("linux")){
            System.out.println(string);
        } else if (osname.toLowerCase().contains("windows")) {
            winprint(string);
        } else {
            //unknown os
            System.out.println(string);
        }
    }
    private static String getOs() {
        return System.getProperty("os.name");
    }

    private static void winprint(String s) {
        try {
            new ProcessBuilder("cmd", "/c", "echo " + s).inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
