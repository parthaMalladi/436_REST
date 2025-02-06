import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print("Enter City Name (Enter 'Quit' to Exit): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            String link = "http://api.openweathermap.org/data/2.5/weather?q=" + input + "&APPID=ff7f22463aef23ef6d3d620fb0868653";
            String apiCall = getHTML(link);
            if (apiCall.equals("invalid")) {
                System.out.println("Invalid Name or Error, please re-enter the city name");
                continue;
            }
            String temperature = getValue("temp", apiCall);
            double num = Double.parseDouble(temperature);
            num = num - 273.15;
            System.out.println("The temperature in " + input + " is " + String.format("%.2f", num) + " degrees Celcius");
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
                return "invalid";
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

    public static String getValue(String key, String json) {
        int index = json.indexOf("\"" + key + "\":");
        int offset = index + key.length() + 3;
        int i = offset;

        while (json.charAt(i) != ',') {
            i++;
        }

        return json.substring(offset, i);
    }
}