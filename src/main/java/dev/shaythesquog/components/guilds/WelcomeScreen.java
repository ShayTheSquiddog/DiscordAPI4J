package dev.shaythesquog.components.guilds;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import org.jetbrains.annotations.Nullable;

/**
 * @see <a href="https://discord.com/developers/docs/resources/guild#welcome-screen-object">Welcome Screen</a>
 */
public class WelcomeScreen implements JsonAPIComponent {
    @Nullable private final String description;
    private final WelcomeScreenChannel[] welcome_channels;

    public WelcomeScreen(JsonObject data) {
        this.description = data.get("description").isJsonNull() ? null : data.get("description").getAsString();
        welcome_channels = data.getAsJsonArray("welcome_channels").asList().stream().map(channelData -> new WelcomeScreenChannel(channelData.getAsJsonObject())).toArray(WelcomeScreenChannel[]::new);
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("description", description);
        JsonArray channels = new JsonArray(welcome_channels.length);
        for (WelcomeScreenChannel channel : welcome_channels)
            channels.add(channel.toJson());
        data.add("welcome_channels", channels);
        return data;
    }

    /**
     * @see <a href="https://discord.com/developers/docs/resources/guild#welcome-screen-object-welcome-screen-channel-structure">Welcome Screen Channel Structure</a>
     */
    public static class WelcomeScreenChannel extends SnowflakeIdentifiable implements JsonAPIComponent {
        private final String description;
        @Nullable private final Snowflake emoji_id;
        @Nullable private final String emoji_name;

        public WelcomeScreenChannel(JsonObject data) {
            super(Snowflake.of(data.get("channel_id").getAsString()));
            description = data.get("description").getAsString();
            emoji_id = data.get("emoji_id").isJsonNull() ? null : Snowflake.of(data.get("emoji_id").getAsString());
            emoji_name = data.get("emoji_name").isJsonNull() ? null : data.get("emoji_name").getAsString();
        }

        public JsonObject toJson() {
            JsonObject data = new JsonObject();
            data.addProperty("channel_id", id.toString());
            data.addProperty("description", description);
            data.addProperty("emoji_id", emoji_id == null ? null : emoji_id.toString());
            data.addProperty("emoji_name", emoji_name);
            return data;
        }
    }
}
