package dev.shaythesquog.components.guilds;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import dev.shaythesquog.components.users.User;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/resources/sticker#sticker-object-sticker-structure">Sticker Structure</a>
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Sticker extends SnowflakeIdentifiable implements JsonAPIComponent {
    private final Optional<Snowflake> pack_id;
    private final String name;
    @Nullable private final String description;
    private final String tags;
    private final StickerType type;
    private final StickerFormat format_type;
    private final Optional<Boolean> available;
    private final Optional<Snowflake> guild_id;
    private final Optional<User> user;
    private final Optional<Integer> sort_value;

    public Sticker(JsonObject data) {
        super(Snowflake.of(data.get("id").getAsString()));
        pack_id = Optional.ofNullable(data.has("pack_id") ? Snowflake.of(data.get("pack_id").getAsString()) : null);
        name = data.get("name").getAsString();
        description = data.get("description").isJsonNull() ? null : data.get("description").getAsString();
        tags = data.get("tags").getAsString();
        type = StickerType.valueOf(data.get("type").getAsInt());
        format_type = StickerFormat.valueOf(data.get("format_type").getAsInt());
        available = Optional.ofNullable(data.has("available") ? data.get("available").getAsBoolean() : null);
        guild_id = Optional.ofNullable(data.has("guild_id") ? Snowflake.of(data.get("guild_id").getAsString()) : null);
        user = Optional.ofNullable(data.has("user") ? new User(data.getAsJsonObject("user")) : null);
        sort_value = Optional.ofNullable(data.has("sort_value") ? data.get("sort_value").getAsInt() : null);
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        pack_id.ifPresent(sf -> data.addProperty("pack_id", sf.toString()));
        data.addProperty("name", name);
        data.addProperty("description", description);
        data.addProperty("tags", tags);
        data.addProperty("type", type.value);
        data.addProperty("format_type", format_type.value);
        available.ifPresent(avail -> data.addProperty("available", avail));
        guild_id.ifPresent(guid -> data.addProperty("guild_id", guid.toString()));
        user.ifPresent(user -> data.add("user", user.toJson()));
        sort_value.ifPresent(sort_value -> data.addProperty("sort_value", sort_value));
        return data;
    }

    /**
     * @see <a href="https://discord.com/developers/docs/resources/sticker#sticker-object-sticker-types">Sticker Types</a>
     */
    public enum StickerType {
        STANDARD(1),
        GUILD(2);

        public final int value;
        StickerType(int value) { this.value = value; }

        public static StickerType valueOf(int value) {
            return switch(value) {
                case 1 -> STANDARD;
                case 2 -> GUILD;
                default -> null;
            };
        }
    }


    /**
     * @see <a href="https://discord.com/developers/docs/resources/sticker#sticker-object-sticker-format-types">Sticker Format Types</a>
     */
    public enum StickerFormat {
        PNG(1),
        APNG(2),
        LOTTIE(3),
        GIF(4);

        public final int value;
        StickerFormat(int value) { this.value = value; }

        public static StickerFormat valueOf(int value) {
            return switch (value) {
                case 1 -> PNG;
                case 2 -> APNG;
                case 3 -> LOTTIE;
                case 4 -> GIF;
                default -> null;
            };
        }
    }
}
