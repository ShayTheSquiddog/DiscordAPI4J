package dev.shaythesquog.components.guilds.channels;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import dev.shaythesquog.components.guilds.Permission;

public class Overwrite extends SnowflakeIdentifiable implements JsonAPIComponent {
    private final Type type;
    private final Permission[] allow;
    private final Permission[] deny;

    public Overwrite(JsonObject data) {
        super(Snowflake.of(data.get("id").getAsString()));
        type = Type.values()[data.get("type").getAsInt()];
        allow = Permission.getPermissionsFromBigInteger(data.get("allow").getAsBigInteger());
        deny = Permission.getPermissionsFromBigInteger(data.get("deny").getAsBigInteger());
    }

    @Override
    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        data.addProperty("type", type.ordinal());
        data.addProperty("allow", Permission.getPermissionsAsBigInteger(allow));
        data.addProperty("deny", Permission.getPermissionsAsBigInteger(deny));
        return data;
    }

    private enum Type{
        role,
        member
    }
}
