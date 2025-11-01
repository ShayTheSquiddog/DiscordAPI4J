import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
//import dev.shaythesquog.components.DiscordAPIAgent;
import dev.shaythesquog.components.guilds.Guild;
import dev.shaythesquog.components.users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@SuppressWarnings("CommentedOutCode")
public class ParsingTests {
//    private static final DiscordAPIAgent discordAPIAgent;
//    static {
//        JsonObject data;
//        try (InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("bot_data.json")) {
//            if(resource == null) {
//                throw new IOException("Resource not found!");
//            }
//            JsonReader reader = new JsonReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
//            data = JsonParser.parseReader(reader).getAsJsonObject();
//            reader.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        discordAPIAgent = new DiscordAPIAgent(data.get("botToken").getAsString(),
//                                              data.get("clientID").getAsString(),
//                                              data.get("clientSecret").getAsString());
//    }

    @Test
    public void testUserParsing() {
        JsonObject originJsonObject = readJsonResource("example_user.json");
        User user = new User(originJsonObject);
        assertJsonElementEquals(originJsonObject, user.toJson());
    }

    @Test
    public void testGuildParsing() {
        JsonObject originJsonObject = readJsonResource("example_guild.json");
        Guild guild = new Guild(originJsonObject);
        assertJsonElementEquals(originJsonObject, guild.toJson());
    }

    private JsonObject readJsonResource(String path) {
        JsonObject data;
        try (InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if(resource == null) {
                throw new IOException("Resource not found!");
            }

            JsonReader reader = new JsonReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
            data = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();

            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertJsonElementEquals(JsonElement expectedJson, JsonElement actualJson) {
        if(expectedJson.isJsonNull()) {
            Assertions.assertTrue(actualJson.isJsonNull());
        } else if(expectedJson.isJsonPrimitive()) {
            Assertions.assertEquals(expectedJson.getAsJsonPrimitive().getAsString(), actualJson.getAsJsonPrimitive().getAsString());
        } if(expectedJson.isJsonArray()) {
            List<JsonElement> expectedJsonArr = expectedJson.getAsJsonArray().asList();
            List<JsonElement> actualJsonArr = actualJson.getAsJsonArray().asList();
            Assertions.assertEquals(expectedJsonArr.size(), actualJsonArr.size());
            for(int i = 0; i < expectedJsonArr.size(); i++) {
                assertJsonElementEquals(expectedJsonArr.get(i), actualJsonArr.get(i));
            }
        } else if (expectedJson.isJsonObject()) {
            // This is a json object
            Map<String, JsonElement> expectedJsonMap = expectedJson.getAsJsonObject().asMap();
            Map<String, JsonElement> actualJsonMap = actualJson.getAsJsonObject().asMap();
            // Assert that the two contain one another
            expectedJsonMap.keySet().forEach(key -> Assertions.assertTrue(actualJsonMap.containsKey(key)));
            actualJsonMap.keySet().forEach(key -> Assertions.assertTrue(expectedJsonMap.containsKey(key)));

            expectedJsonMap.forEach((key, value) -> assertJsonElementEquals(value, actualJsonMap.get(key)));
        } else {
            Assertions.assertEquals(expectedJson.toString(), actualJson.toString());
        }
    }
}
