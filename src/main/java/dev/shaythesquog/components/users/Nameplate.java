package dev.shaythesquog.components.users;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.AbstractAPIComponent;
import dev.shaythesquog.components.Snowflake;

/**
 * @see <a href="https://discord.com/developers/docs/resources/user#nameplate">Nameplate</a>
 */
public class Nameplate extends AbstractAPIComponent {
    private final Snowflake sku_id;
    private final String asset;
    private final String label;
    private final Palette palette;

    public Nameplate(JsonObject data) {
        sku_id = new Snowflake(data.get("sku_id").getAsString());
        asset = data.get("asset").getAsString();
        label = data.get("label").getAsString();
        palette = Palette.valueOf(data.get("palette").getAsString());
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("sku_id", sku_id.toString());
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
