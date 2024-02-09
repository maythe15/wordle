import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.*;

public class Definition {
    public static ArrayList<String> getDefinition(String word) throws IOException {
        URL url = new URL("http://api.dictionaryapi.dev/api/v2/entries/en/"+word);
        HttpURLConnection con;
        StringBuffer content;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }

        Pattern rpat=Pattern.compile("\"definition\":\"(.+?)\"");
        Matcher matcher = rpat.matcher(content);

        ArrayList<String> res = new ArrayList<>();
        while (matcher.find()) {
            res.add(matcher.group(1));
        }
        return res;
    }
}
