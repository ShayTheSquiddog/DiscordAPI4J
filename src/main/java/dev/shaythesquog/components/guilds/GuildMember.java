package dev.shaythesquog.components.guilds;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.users.AvatarDecorationData;
import dev.shaythesquog.components.users.User;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class GuildMember implements JsonAPIComponent {
    private final Optional<User> user;
    @Nullable private final Optional<String> nick;
    @Nullable private final Optional<String> avatar;
    @Nullable private final Optional<String> banner;
    private final Snowflake[] roles;
    @Nullable private final Instant joined_at;
    @Nullable private final Optional<Instant> premium_since;
    private final boolean deaf;
    private final boolean mute;
    private final GuildMemberFlags[] flags;
    private final Optional<Boolean> pending;
    private final Optional<String> permissions;
    @Nullable private final Optional<Instant> communication_disabled_until;
    @Nullable private final Optional<AvatarDecorationData> avatar_decoration_data;
    private final Snowflake guildId;

    public GuildMember(JsonObject data, Snowflake guildId) {
        user = Optional.ofNullable(data.has("user") ? new User(data.get("user").getAsJsonObject()) : null);
        nick = data.has("nick") ? (data.get("nick").isJsonNull() ? null : Optional.of(data.get("nick").getAsString())) : Optional.empty();
        avatar = data.has("avatar") ? (data.get("avatar").isJsonNull() ? null : Optional.of(data.get("avatar").getAsString())) : Optional.empty();
        banner = data.has("banner") ? (data.get("banner").isJsonNull() ? null : Optional.of(data.get("banner").getAsString())) : Optional.empty();
        roles = data.get("roles").getAsJsonArray().asList().stream().map(roleSF -> Snowflake.of(roleSF.getAsString())).toArray(Snowflake[]::new);
        joined_at = data.has("joined_at") ? Instant.parse(data.get("joined_at").getAsString()) : null;
        premium_since = data.has("premium_since") ? (data.get("premium_since").isJsonNull() ? null : Optional.of(Instant.parse(data.get("premium_since").getAsString()))) : Optional.empty();
        deaf = data.get("deaf").getAsBoolean();
        mute = data.get("mute").getAsBoolean();
        flags = GuildMemberFlags.getFlagsFromInt(data.get("flags").getAsInt());
        pending = Optional.ofNullable(data.has("pending") ? data.get("pending").getAsBoolean() : null);
        permissions = Optional.ofNullable(data.has("permissions") ? data.get("permissions").getAsString() : null);
        communication_disabled_until = data.has("communication_disabled_until") ? (data.get("communication_disabled_until").isJsonNull() ? null : Optional.of(Instant.parse(data.get("communication_disabled_until").getAsString()))) : Optional.empty();
        avatar_decoration_data = data.has("avatar_decoration_data") ? (data.get("avatar_decoration_data").isJsonNull() ? null : Optional.of(new AvatarDecorationData(data.getAsJsonObject("avatar_decoration_data")))) : Optional.empty();
        this.guildId = guildId;
    }

    @Override
    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        user.ifPresent(userObj -> data.add("user", userObj.toJson()));
        if (nick == null) data.add("nick", null);
        else nick.ifPresent(nickObj -> data.addProperty("nick", nickObj));
        if (avatar == null) data.add("avatar", null);
        else avatar.ifPresent(avatarObj -> data.addProperty("avatar", avatarObj));
        if (banner == null) data.add("banner", null);
        else banner.ifPresent(bannerObj -> data.addProperty("banner", bannerObj));

        JsonArray rolesJson = new JsonArray();
        for (Snowflake roleSF : roles)
            rolesJson.add(roleSF.toString());
        data.add("roles", rolesJson);

        data.addProperty("joined_at", joined_at == null ? null : joined_at.toString());
        if (premium_since == null) data.add("premium_since", null);
        else premium_since.ifPresent(premiumInstant -> data.addProperty("premium_since", premiumInstant.toString()));
        data.addProperty("deaf", deaf);
        data.addProperty("mute", mute);
        data.addProperty("flags", GuildMemberFlags.getFlagsAsInt(flags));
        pending.ifPresent(pendingObj -> data.addProperty("pending", pendingObj));
        permissions.ifPresent(permissionsObj -> data.addProperty("permissions", permissionsObj));
        if (communication_disabled_until == null) data.add("communication_disabled_until", null);
        else
            communication_disabled_until.ifPresent(cduInstant -> data.addProperty("communication_disabled_until", cduInstant.toString()));
        if (avatar_decoration_data == null) data.add("avatar_decoration_data", null);
        else avatar_decoration_data.ifPresent(addObj -> data.add("avatar_decoration_data", addObj.toJson()));

        return data;
    }

    public String avatarURL() {
        if (user.isEmpty() || avatar == null || avatar.isEmpty())
            return user.map(User::avatarURL).orElse(null);
        StringBuilder avatarURL = new StringBuilder(String.format("https://cdn.discordapp.com/guilds/%s/users/%s/avatars/%s", guildId.toString(), user.get().getId().toString(), avatar.get()));
        if (avatar.get().startsWith("a_"))
            avatarURL.append(".gif");
        else
            avatarURL.append(".png");
        return avatarURL.toString();
    }

    @SuppressWarnings({"Duplicates", "unused"})
    public String avatarURL(int size) {
        final int[] validSizes = new int[]{16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
        Arrays.sort(validSizes);
        if (Arrays.binarySearch(validSizes, size) < 0)
            throw new IllegalArgumentException("Invalid size: " + size);
        return avatarURL() + "?size=" + size;
    }

    @SuppressWarnings("unused")
    public String getDisplayName() {
        if (nick != null && nick.isPresent())
            return nick.get();
        else
            return user.map(User::getDisplayName).orElse(null);
    }

    public enum GuildMemberFlags {
        DID_REJOIN,
        COMPLETED_ONBOARDING,
        BYPASSES_VERIFICATION,
        STARTED_ONBOARDING,
        IS_GUEST,
        STARTED_HOME_ACTIONS,
        COMPLETED_HOME_ACTIONS,
        AUTOMOD_QUARANTINED_USERNAME,
        DM_SETTINGS_UPSELL_ACKNOWLEDGED(9),
        AUTOMOD_QUARANTINED_GUILD_TAG(10);

        public final int value;

        GuildMemberFlags() {
            this.value = ordinal();
        }

        GuildMemberFlags(int value) {
            this.value = value;
        }

        public static int getFlagsAsInt(GuildMemberFlags... flags) {
            int result = 0;
            for (GuildMemberFlags flag : flags) {
                result += (1 << flag.value);
            }
            return result;
        }

        public static GuildMemberFlags[] getFlagsFromInt(int flagsInt) {
            List<GuildMemberFlags> result = new LinkedList<>();
            for (GuildMemberFlags flag : GuildMemberFlags.values()) {
                if ((flagsInt >> flag.value) % 2 == 1) {
                    result.add(flag);
                }
            }
            return result.toArray(new GuildMemberFlags[0]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(getClass() == o.getClass()) {
            if(user.isPresent() && ((GuildMember) o).user.isPresent()) {
                return user.equals(((GuildMember) o).user);
            }
        }

        return false;
    }
}
