import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String apiKey = "";
        String city = "London";
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + apiKey;
        String s = getHTML(url);
        Map<String, String> geoMap = parseJson(s);

        for (Map.Entry<String, String> entry : geoMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println( key + " : " + value);
        }
    }

    // helper function to get the HTML body from the URL
    private static String getHTML(String link) {
        try {
            URL url = new URL(link);
            
            URLConnection connection = (HttpURLConnection) url.openConnection();


            ((HttpURLConnection) connection).setRequestMethod("GET");
            int responseCode = ((HttpURLConnection) connection).getResponseCode();

            // if a valid request, return the body. Otherwise return 'invalid' and the error code
            if (responseCode == 200) {
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());

                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }
                scanner.close();
                return sb.toString();
            } else {
                return "invalid" + responseCode;
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        } catch (ProtocolException e) {
            System.out.println("Protocol Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Exception: " + e.getMessage());
        }

        return "";
    }

    public static Map<String, String> parseJson(String json) {
        String cleanedUp = json.trim().replaceAll("[{}\"]", "");
        String[] pairings = cleanedUp.split(",");

        Map<String, String> map = new HashMap<>();

        for (String pair : pairings) {
            String[] entry = pair.split(":");
            String key = entry[0].trim();
            String value = entry[1].trim();

            map.put(key, value);
        }

        return map;
    }
}