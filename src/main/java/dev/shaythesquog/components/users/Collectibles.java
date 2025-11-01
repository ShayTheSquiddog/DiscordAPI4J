package dev.shaythesquog.components.users;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;

import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/resources/user#collectibles">Collectibles</a>
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Collectibles implements JsonAPIComponent {
    private final Optional<Nameplate> nameplate;

    public Collectibles(JsonObject data) {
        nameplate = Optional.ofNullable(data.has("nameplate") ?
                new Nameplate(data.getAsJsonObject("nameplate")) : null);
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        nameplate.ifPresent(value -> data.add("nameplate", value.toJson()));
        return data;

    }
}
