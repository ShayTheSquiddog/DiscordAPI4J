package dev.shaythesquog.components.guilds;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import org.jetbrains.annotations.Nullable;

public class PartialGuild extends SnowflakeIdentifiable implements JsonAPIComponent {
    private final String name;
    @Nullable private final String icon;

    public PartialGuild(JsonObject data) {
        super(Snowflake.of(data.get("id").getAsString()));
        icon = data.get("icon").isJsonNull() ? null : data.get("icon").getAsString();
        name = data.get("name").getAsString();
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        data.addProperty("name", name);
        data.addProperty("icon", icon);
        return data;
    }
}
