package dev.shaythesquog.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.shaythesquog.components.users.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SuppressWarnings("unused")
public class DiscordAPIConnection {
    private static final String API_ENDPOINT = "https://discord.com/api/v10";
    private final String encodedCredentials;
    private final String botToken;
    private JsonObject oAuth2Token;

    public DiscordAPIConnection(String botToken) {
        this.botToken = botToken;
        encodedCredentials = null;
    }

    public DiscordAPIConnection(String botToken, String clientID, String clientSecret) {
        this.botToken = botToken;
        encodedCredentials = Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
    }

    public void genOAuth2Token() {
        if(encodedCredentials == null)
            throw new IllegalStateException("The API can't generate an OAuth token without client credentials.");

        URL url = constructURL("/oauth2/token");

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            connection.setDoOutput(true);

            final String body = "grant_type=client_credentials&scope=identify%20connections%20bot";
            String response = doSendReceive(connection, body);
            oAuth2Token = JsonParser.parseString(response).getAsJsonObject();

        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

    public int revokeToken() {
        if(oAuth2Token == null || oAuth2Token.get("access_token") == null)
            return -1;

        URL url = constructURL("/oauth2/token/revoke");

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            connection.setDoOutput(true);

            final String body = String.format("token=%s&token_type_hint=access_token", oAuth2Token.get("access_token"));
            doSendReceive(connection, body);
            oAuth2Token = null;
            return connection.getResponseCode();

        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

    public JsonObject getCurrentAuthInfo() {
        if(oAuth2Token == null || oAuth2Token.get("access_token") == null)
            throw new IllegalStateException("The API can't get current auth info without an auth token.");

        URL url = constructURL("/oauth2/@me");

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + oAuth2Token.get("access_token").getAsString());
            connection.setDoOutput(false);

            String response = receiveResponse(connection);

            return JsonParser.parseString(response).getAsJsonObject();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

    public User getUser(long userId) {
        URL url = constructURL("/users/" + Long.toUnsignedString(userId));

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bot " + botToken);
            connection.setDoOutput(false);

            String response = receiveResponse(connection);

            return new User(JsonParser.parseString(response).getAsJsonObject());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

    private static String doSendReceive(HttpURLConnection connection, String data) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
            dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();
        }

        return receiveResponse(connection);
    }

    private static String receiveResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                response.append(line);
                System.out.println(line);
            }
        }
        return response.toString();
    }

    private URL constructURL(String path) {
        try {
            return new URI(String.format("%s%s", API_ENDPOINT, path)).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
