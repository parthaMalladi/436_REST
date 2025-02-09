import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String link = "";

        while (true) {
            System.out.print("\nEnter City Name (Enter 'Quit' to Exit): ");
            input = scanner.nextLine();
            String output;

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            // parameters to get weather data
            String[][] weatherHeaders = {};
            link = "http://api.openweathermap.org/data/2.5/weather?q=" + input + "&APPID=ff7f22463aef23ef6d3d620fb0868653";
            output = getInfo(link, weatherHeaders, "temp");
            if (output.equals("invalid")) {
                System.out.println("Invalid Name or Error, please re-enter the city name");
                continue;
            }
            double temp = Double.parseDouble(output) - 273.15;
            System.out.println("The temperature in " + input + " is " + String.format("%.2f", temp) + " degrees Celcius.");

            // parameters to get population data
            String[][] populationHeaders = {
                {"X-Api-Key", "0xSuV9jLP2qCg85M2Yw7CA==abxpVXafPWf6pZy6"},
                {"accept", "application/json"}
            };
            link = "https://api.api-ninjas.com/v1/city?name=" + input;
            output = getInfo(link, populationHeaders, "population");
            if (output.equals("invalid")) {
                System.out.println("Invalid Name or Error, please re-enter the city name");
                continue;
            }
            System.out.println("The population of " + input + " is " + output.substring(1) + " people.");

            // parameters to get country
            String[][] countryHeaders = {
                {"x-rapidapi-key", "feb8505df7mshc819e40e5708dbdp159311jsn5e5d142d62df"},
                {"Accept", "application/json"}
            };
            link = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?namePrefix=" + input + "&limit=1&sort=-population";
            output = getInfo(link, countryHeaders, "country");
            if (output.equals("invalid")) {
                System.out.println("Invalid Name or Error, please re-enter the city name");
                continue;
            }
            System.out.println(input + " is located in " + output.substring(1, output.length() - 1));
        }
    }

    private static String getInfo(String link, String[][] headers, String key) {
        String json = getJSON(link, headers);
        if (json.equals("invalid")) {
            return json;
        }

        return getValue(key, json);
    }

    private static String getJSON(String link, String[][] headers) {
        try {
            URL url = new URL(link);
            
            URLConnection connection = (HttpURLConnection) url.openConnection();
            ((HttpURLConnection) connection).setRequestMethod("GET");

            for (String[] pairs : headers) {
                connection.setRequestProperty(pairs[0], pairs[1]);
            }
            
            int responseCode = ((HttpURLConnection) connection).getResponseCode();
            
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

    private static String getValue(String key, String json) {
        int index = json.indexOf("\"" + key + "\":");
        int offset = index + key.length() + 3;
        int i = offset;

        while (json.charAt(i) != ',') {
            i++;
        }

        return json.substring(offset, i);
    }
}