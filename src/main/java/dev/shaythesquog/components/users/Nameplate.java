package dev.shaythesquog.components.users;

import com.google.gson.*;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;


/**
 * @see <a href="https://discord.com/developers/docs/resources/user#nameplate">Nameplate</a>
 */
public class Nameplate extends SnowflakeIdentifiable implements JsonAPIComponent {
    private final String asset;
    private final String label;
    private final Palette palette;

    public Nameplate(JsonObject data) {
        super(Snowflake.of(data.get("sku_id").getAsString()));
        asset = data.get("asset").getAsString();
        label = data.get("label").getAsString();
        palette = Palette.valueOf(data.get("palette").getAsString());
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("sku_id", id.toString());
        data.addProperty("asset", asset);
        data.addProperty("label", label);
        data.addProperty("palette", palette.toString());
        return data;
    }

    public enum Palette{
        crimson,
        berry,
        sky,
        teal,
        forest,
        bubble_gum,
        violet,
        cobalt,
        clover,
        lemon,
        white
    }
}
