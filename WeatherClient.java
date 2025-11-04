// WeatherClient.java
// Demonstrates a REST API Client in Java using HttpURLConnection
// Fetches live weather data from Open-Meteo public API and displays it in structured format

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;  // JSON parsing library (add org.json dependency)

public class WeatherClient {

    public static void main(String[] args) {
        try {
            // 🌦 Example API endpoint (No API key needed)
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=12.97&longitude=77.59&current_weather=true";
            
            // 1️⃣ Create URL object
            URL url = new URL(urlString);
            
            // 2️⃣ Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // 3️⃣ Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // OK
                // 4️⃣ Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 5️⃣ Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject currentWeather = jsonResponse.getJSONObject("current_weather");

                // 6️⃣ Display data in structured format
                System.out.println("=== 🌤 Current Weather Data (Bangalore) ===");
                System.out.println("Temperature: " + currentWeather.getDouble("temperature") + "°C");
                System.out.println("Wind Speed:  " + currentWeather.getDouble("windspeed") + " km/h");
                System.out.println("Wind Direction: " + currentWeather.getDouble("winddirection") + "°");
                System.out.println("Time: " + currentWeather.getString("time"));
                System.out.println("===========================================");

            } else {
                System.out.println("❌ Failed to fetch data. HTTP Response Code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            System.out.println("⚠ Error: " + e.getMessage());
        }
    }
}