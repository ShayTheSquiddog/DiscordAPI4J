package dev.shaythesquog.components.guilds;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/topics/permissions#role-object>Role Object</a>
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class Role implements JsonAPIComponent {
    private final Snowflake id;
    private final String name;
    private final RoleColors colors;
    private final Boolean hoist;
    @Nullable private final Optional<String> icon;
    @Nullable private final Optional<String> unicode_emoji;
    private final Integer position;
    private final String permissions;
    private final Boolean managed;
    private final Boolean mentionable;
    private final Optional<RoleTags> tags;
    private final RoleFlags[] flags;

    public Role(JsonObject data) {
        id = new Snowflake(data.get("id").getAsString());
        name = data.get("name").getAsString();
        colors = new RoleColors(data.getAsJsonObject("colors"));
        hoist = data.get("hoist").getAsBoolean();
        icon = data.has("icon") ? (data.get("icon").isJsonNull() ? null : Optional.of(data.get("icon").getAsString())) : Optional.empty();
        unicode_emoji = data.has("unicode_emoji") ? (data.get("unicode_emoji").isJsonNull() ? null : Optional.of(data.get("unicode_emoji").getAsString())) : Optional.empty();
        position = data.get("position").getAsInt();
        permissions = data.get("permissions").getAsString();
        managed = data.get("managed").getAsBoolean();
        mentionable = data.get("mentionable").getAsBoolean();
        tags = Optional.ofNullable(data.has("tags") ? new RoleTags(data.getAsJsonObject("tags")) : null);
        flags = RoleFlags.getFlagsFromInt(data.get("flags").getAsInt());
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        data.addProperty("name", name);
        data.add("colors", colors.toJson());
        data.addProperty("hoist", hoist);
        if(icon == null) data.add("icon", null);
        else icon.ifPresent(ico -> data.addProperty("icon", ico));
        if(unicode_emoji == null) data.add("unicode_emoji", null);
        else unicode_emoji.ifPresent(ue -> data.addProperty("unicode_emoji", ue));
        data.addProperty("position", position);
        data.addProperty("permissions", permissions);
        data.addProperty("managed", managed);
        data.addProperty("mentionable", mentionable);
        tags.ifPresent(roleTags -> data.add("tags", roleTags.toJson()));
        data.addProperty("flags", RoleFlags.getFlagsAsInt(flags));
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == getClass())
            return id.equals(((Role) o).id);
        return false;
    }

    public static class RoleColors implements JsonAPIComponent {
        private final Integer primary_color;
        @Nullable private final Integer secondary_color;
        @Nullable private final Integer tertiary_color;

        public RoleColors(JsonObject data) {
            primary_color = data.get("primary_color").getAsInt();
            secondary_color = data.get("secondary_color").isJsonNull() ? null : data.get("secondary_color").getAsInt();
            tertiary_color = data.get("tertiary_color").isJsonNull() ? null : data.get("tertiary_color").getAsInt();
        }

        public JsonObject toJson() {
            JsonObject data = new JsonObject();
            data.addProperty("primary_color", primary_color);
            data.addProperty("secondary_color", secondary_color);
            data.addProperty("tertiary_color", tertiary_color);
            return data;
        }
    }

    public static class RoleTags implements JsonAPIComponent {
        private final Optional<Snowflake> bot_id;
        private final Optional<Snowflake> integration_id;
        private final boolean premium_subscriber;
        private final Optional<Snowflake> subscription_listing_id;
        private final boolean available_for_purchase;
        private final boolean guild_connections;

        public RoleTags(JsonObject data) {
            bot_id = Optional.ofNullable(data.has("bot_id") ? new Snowflake(data.get("bot_id").getAsString()) : null);
            integration_id = Optional.ofNullable(data.has("integration_id") ? new Snowflake(data.get("integration_id").getAsString()) : null);
            premium_subscriber = data.has("premium_subscriber");
            subscription_listing_id = Optional.ofNullable(data.has("subscription_listing_id") ? new Snowflake(data.get("subscription_listing_id").getAsString()) : null);
            available_for_purchase = data.has("available_for_purchase");
            guild_connections = data.has("guild_connections");
        }

        public JsonObject toJson() {
            JsonObject data = new JsonObject();
            bot_id.ifPresent(bid -> data.addProperty("bot_id", bid.toString()));
            integration_id.ifPresent(iid -> data.addProperty("integration_id", iid.toString()));
            if(premium_subscriber) data.add("premium_subscriber", null);
            subscription_listing_id.ifPresent(sid -> data.addProperty("subscription_listing_id", sid.toString()));
            if(available_for_purchase) data.add("available_for_purchase", null);
            if(guild_connections) data.add("guild_connections", null);
            return data;
        }
    }

    public enum RoleFlags {
        IN_PROMPT;

        public static int getFlagsAsInt(RoleFlags... roleFlags) {
            int flags = 0;
            for(RoleFlags flag : roleFlags)
                flags += 1 << flag.ordinal();
            return flags;
        }

        public static RoleFlags[] getFlagsFromInt(int roleFlags) {
            List<RoleFlags> list = new LinkedList<>();
            for (RoleFlags flag : RoleFlags.values())
                if((roleFlags >> flag.ordinal()) % 2 == 1)
                    list.add(flag);
            return list.toArray(new RoleFlags[0]);
        }
    }
}
