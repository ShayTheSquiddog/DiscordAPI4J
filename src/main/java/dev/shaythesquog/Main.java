package dev.shaythesquog;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import dev.shaythesquog.components.DiscordAPIAgent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.Util;
import dev.shaythesquog.components.guilds.Webhook;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DiscordAPIAgent agent = buildGenericBotFromResources();
        List<Webhook> webhook = agent.listGuildWebhooks(Snowflake.of("1433966725510070324"));
        for(Webhook w : webhook) {
            System.out.println(getPrettyJson(w.toJson()));
        }
    }

    private static DiscordAPIAgent buildGenericBotFromResources() {
        JsonObject data;
        try (InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("bot_data.json")) {
            if(resource == null) {
                throw new IOException("Resource not found!");
            }
            JsonReader reader = new JsonReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
            data = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new DiscordAPIAgent(data.get("botToken").getAsString(),
                                              data.get("clientID").getAsString(),
                                              data.get("clientSecret").getAsString());
    }

    public static String getPrettyJson(JsonElement json) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(json);
    }
}