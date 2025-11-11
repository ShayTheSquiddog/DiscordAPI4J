package dev.shaythesquog.components.guilds.channels;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import org.jetbrains.annotations.Nullable;

public class DefaultReaction extends SnowflakeIdentifiable implements JsonAPIComponent {
    @Nullable private final String emoji_name;

    public DefaultReaction(JsonObject data) {
        super(data.get("emoji_id").isJsonNull() ? null : Snowflake.of(data.get("emoji_id").getAsString()));
        emoji_name = data.get("emoji_name").isJsonNull() || !data.get("emoji_id").isJsonNull() ? null : data.get("emoji_name").getAsString();
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        if(id != null) {
            data.addProperty("emoji_id", id.toString());
            data.add("emoji_name", null);
        } else {
            data.add("emoji_id", null);
            data.addProperty("emoji_name", emoji_name);
        }
        return data;
    }
}
