package dev.shaythesquog.components.guilds.channels;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import dev.shaythesquog.components.Util;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PartialChannel extends SnowflakeIdentifiable implements JsonAPIComponent {
    @Nullable private final Optional<String> name;

    public PartialChannel(JsonObject data) {
        super(Snowflake.of(data.get("id").getAsString()));
        name = data.has("name") ? (data.get("name").isJsonNull() ? null : Optional.of(data.get("name").getAsString())) : Optional.empty();
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        Util.addNullableOptionalIfPresent(name, "name", data);
        return data;
    }
}
