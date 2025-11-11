package dev.shaythesquog.components.users;


import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import org.jetbrains.annotations.Nullable;

/**
 * @see <a href="https://discord.com/developers/docs/resources/user#user-object-user-primary-guild">User Primary Guilds</a>
 */
public class UserPrimaryGuild implements JsonAPIComponent {
    @Nullable private final Snowflake identity_guild_id;
    @Nullable private final Boolean identity_enabled;
    @Nullable private final String tag;
    @Nullable private final String badge;

    public UserPrimaryGuild(JsonObject userJson) {
        identity_guild_id = userJson.get("identity_guild_id").isJsonNull() ? null : Snowflake.of(userJson.get("identity_guild_id").getAsString());
        identity_enabled = userJson.get("identity_enabled").isJsonNull() ? null : userJson.get("identity_enabled").getAsBoolean();
        tag = userJson.get("tag").isJsonNull() ? null : userJson.get("tag").getAsString();
        badge = userJson.get("badge").isJsonNull() ? null : userJson.get("badge").getAsString();
    }

    @Override
    public JsonObject toJson() {
        JsonObject userJson = new JsonObject();
        userJson.add("identity_guild_id", identity_guild_id != null ? new JsonPrimitive(identity_guild_id.toString()) : JsonNull.INSTANCE);
        userJson.addProperty("identity_enabled", identity_enabled);
        userJson.addProperty("tag", tag);
        userJson.addProperty("badge", badge);
        return userJson;
    }
}
