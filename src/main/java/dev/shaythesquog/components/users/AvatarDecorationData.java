package dev.shaythesquog.components.users;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;

/**
 * @see <a href="https://discord.com/developers/docs/resources/user#avatar-decoration-data-object">Avatar Decoration Data</a>
 */
public class AvatarDecorationData extends SnowflakeIdentifiable implements JsonAPIComponent {
    private final String asset;

    public AvatarDecorationData(JsonObject data) {
        super(Snowflake.of(data.get("sku_id").getAsString()));
        asset = data.get("asset").getAsString();
    }

    @Override
    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("asset", asset);
        data.addProperty("sku_id", id.toString());
        return data;
    }
}
