package dev.shaythesquog.components.guilds;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/resources/guild#incidents-data-object-incidents-data-structure">Incidents Data Structure</a>
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class IncidentsData implements JsonAPIComponent {
    @Nullable private final Instant invites_disabled_until;
    @Nullable private final Instant dms_disabled_until;
    @Nullable private final Optional<Instant> dm_spam_detected_at;
    @Nullable private final Optional<Instant> raid_detected_at;

    public IncidentsData(JsonObject data) {
        invites_disabled_until = data.get("invites_disabled_until").isJsonNull() ? null : Instant.parse(data.get("invites_disabled_until").getAsString());
        dms_disabled_until = data.get("dms_disabled_until").isJsonNull() ? null : Instant.parse(data.get("dms_disabled_until").getAsString());
        dm_spam_detected_at = data.has("dm_spam_detected_at") ? (data.get("dm_spam_detected at").isJsonNull() ? null : Optional.of(Instant.parse(data.get("dm_spam_detected_at").getAsString()))) : Optional.empty();
        raid_detected_at = data.has("raid_detected_at") ? (data.get("raid_detected_at").isJsonNull() ? null : Optional.of(Instant.parse(data.get("raid_detected_at").getAsString()))) : Optional.empty();
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("invites_disabled_until", invites_disabled_until == null ? null : invites_disabled_until.toString());
        data.addProperty("dms_disable_until", dms_disabled_until == null ? null : dms_disabled_until.toString());
        if(dm_spam_detected_at == null) data.add("dm_spam_detected_at", null);
        else dm_spam_detected_at.ifPresent(dsda -> data.addProperty("dm_spam_detected_at", dsda.toString()));
        if(raid_detected_at == null) data.add("raid_detected_at", null);
        else raid_detected_at.ifPresent(rda -> data.addProperty("raid_detected_at", rda.toString()));
        return data;
    }
}
