package dev.shaythesquog.components.users;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.AbstractAPIComponent;
import dev.shaythesquog.components.Snowflake;

/**
 * @see <a href="https://discord.com/developers/docs/resources/user#avatar-decoration-data-object">Avatar Decoration Data</a>
 */
public class AvatarDecorationData extends AbstractAPIComponent {
    private final String asset;
    private final Snowflake sku_id;

    public AvatarDecorationData(JsonObject data) {
        asset = data.get("asset").getAsString();
        sku_id = new Snowflake(data.get("sku_id").getAsString());
    }

    @Override
    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("asset", asset);
        data.addProperty("sku_id", sku_id.toString());
        return data;
    }
}
