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

        while (true) {
            System.out.print("\nEnter City Name (Enter 'Quit' to Exit): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            String link = "http://api.openweathermap.org/data/2.5/weather?q=" + input + "&APPID=ff7f22463aef23ef6d3d620fb0868653";
            String apiCall = getWeather(link);
            if (apiCall.equals("invalid")) {
                System.out.println("Invalid Name or Error, please re-enter the city name");
                continue;
            }
            String temperature = getValue("temp", apiCall);
            double num = Double.parseDouble(temperature);
            num = num - 273.15;
            System.out.println("The temperature in " + input + " is " + String.format("%.2f", num) + " degrees Celcius.");


            link = "https://api.api-ninjas.com/v1/city?name=" + input;
            apiCall = getPopulation(link);
            if (apiCall.equals("invalid")) {
                System.out.println("Invalid Name or Error, please re-enter the city name");
                continue;
            }
            String population = getValue("population", apiCall);  
            System.out.println("The population of " + input + " is " + population.substring(1) + " people.");      
            
            link = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?namePrefix=" + input + "&limit=1&sort=-population";
            apiCall = getCountry(link);
            if (apiCall.equals("invalid")) {
                System.out.println("Invalid Name or Error, please re-enter the city name");
                continue;
            }
            String country = getValue("country", apiCall);  
            System.out.println(input + " is located in " + country.substring(1, country.length() - 1));
        }
    }

    private static String getWeather(String link) {
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


    private static String getPopulation(String link) {
        try {
            URL url = new URL(link);
            
            URLConnection connection = (HttpURLConnection) url.openConnection();

            ((HttpURLConnection) connection).setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", "0xSuV9jLP2qCg85M2Yw7CA==abxpVXafPWf6pZy6");
            connection.setRequestProperty("accept", "application/json");
            
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

    private static String getCountry(String link) {
        try {
            URL url = new URL(link);
            
            URLConnection connection = (HttpURLConnection) url.openConnection();

            ((HttpURLConnection) connection).setRequestMethod("GET");
            connection.setRequestProperty("x-rapidapi-key", "feb8505df7mshc819e40e5708dbdp159311jsn5e5d142d62df");
            connection.setRequestProperty("Accept", "application/json");
            
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