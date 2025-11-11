package dev.shaythesquog.components.guilds.channels;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import org.jetbrains.annotations.Nullable;

public class Tag implements JsonAPIComponent {
    private final Snowflake id;
    private final String name;
    private final boolean moderated;
    @Nullable private final Snowflake emoji_id;
    @Nullable private final String emoji_name;

    public Tag(JsonObject data) {
        id = Snowflake.of(data.get("id").getAsString());
        name = data.get("name").getAsString();
        moderated = data.get("moderated").getAsBoolean();
        emoji_id = data.get("emoji_id").isJsonNull() ? null : Snowflake.of(data.get("emoji_id").getAsString());
        emoji_name = data.get("emoji_name").isJsonNull() ? null : data.get("emoji_name").getAsString();
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        data.addProperty("name", name);
        data.addProperty("moderated", moderated);
        data.add("emoji_id", emoji_id == null ? null : new JsonPrimitive(emoji_id.toString()));
        data.addProperty("emoji_name", emoji_name);
        return data;
    }

    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()) {
            return id.equals(((Tag)o).id);
        }
        return false;
    }
}
