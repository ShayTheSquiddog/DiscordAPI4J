package dev.shaythesquog.components.guilds;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import dev.shaythesquog.components.users.User;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/resources/emoji#emoji-object">Emoji</a>
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Emoji extends SnowflakeIdentifiable implements JsonAPIComponent {
    @Nullable private final String name;
    private final Optional<Snowflake[]> roles; // Array of role IDs
    private final Optional<User> user;
    private final Optional<Boolean> require_colons;
    private final Optional<Boolean> managed;
    private final Optional<Boolean> animated;
    private final Optional<Boolean> available;

    public Emoji(JsonObject data){
        super(data.get("id").isJsonNull() ? null : Snowflake.of(data.get("id").getAsString()));
        name = data.get("name").isJsonNull() ? null : data.get("name").getAsString();
        roles = Optional.ofNullable(data.has("roles") ?
                data.getAsJsonArray("roles").asList().stream().map(roleID -> Snowflake.of(roleID.getAsString())).toArray(Snowflake[]::new) : null);
        user = Optional.ofNullable(data.has("user") ? new User(data.getAsJsonObject("user")) : null);
        require_colons = Optional.ofNullable(data.has("require_colons") ? data.get("require_colors").getAsBoolean() : null);
        managed = Optional.ofNullable(data.has("managed") ? data.get("managed").getAsBoolean() : null);
        animated = Optional.ofNullable(data.has("animated") ? data.get("animated").getAsBoolean() : null);
        available = Optional.ofNullable(data.has("available") ? data.get("available").getAsBoolean() : null);
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id != null ? id.toString() : null);
        data.addProperty("name", name);
        roles.ifPresent(roleIDs -> {
            JsonArray roleIDArray = new JsonArray();
            for (Snowflake roleID : roleIDs) {
                roleIDArray.add(roleID.toString());
            }
            data.add("roles", roleIDArray);
        });
        user.ifPresent(usr -> data.add("user", usr.toJson()));
        require_colons.ifPresent(col -> data.addProperty("require_colons", col));
        managed.ifPresent(mngd -> data.addProperty("managed", mngd));
        animated.ifPresent(anim -> data.addProperty("animated", anim));
        available.ifPresent(ava -> data.addProperty("available", ava));
        return data;
    }
}
