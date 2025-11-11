package dev.shaythesquog.components.guilds;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Registry;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import dev.shaythesquog.components.guilds.channels.Channel;
import dev.shaythesquog.components.users.User;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @see <a href="https://discord.com/developers/docs/resources/guild#guild-object">Guild</a>
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class Guild extends PartialGuild implements JsonAPIComponent {
        public final Registry<Channel> CHANNEL_REGISTRY = new Registry<>();
        @Nullable private final Optional<String> icon_hash;
        @Nullable private final String splash;
        @Nullable private final String discovery_splash;
        private final Optional<Boolean> owner;
        private final Snowflake owner_id;
        private final Optional<String> permissions;
        @Nullable private final Optional<String> region;
        @Nullable private final Snowflake afk_channel_id;
        private final int afk_timeout;
        private final Optional<Boolean> widget_enabled;
        @Nullable private final Optional<Snowflake> widget_channel_id;
        private final VerificationLevel verification_level;
        private final MessageNotificationLevel default_message_notifications;
        private final ExplicitFilterLevel explicit_content_filter;
        private final Role[] roles;
        private final Emoji[] emojis;
        private final GuildFeature[] features;
        private final MFALevel mfa_level;
        @Nullable private final Snowflake application_id;
        @Nullable private final Snowflake system_channel_id;
        private final SystemChannelFlags[] system_channel_flags;
        @Nullable private final Snowflake rules_channel_id;
        @Nullable private final Optional<Integer> max_presences;
        private final Optional<Integer> max_members;
        @Nullable private final String vanity_url_code;
        @Nullable private final String description;
        @Nullable private final String banner;
        private final PremiumTier premium_tier;
        private final Optional<Integer> premium_subscription_count;
        private final String preferred_locale;
        @Nullable private final Snowflake public_updates_channel_id;
        private final Optional<Integer> max_video_channel_users;
        private final Optional<Integer> max_stage_video_channel_users;
        private final Optional<Integer> approximate_member_count;
        private final Optional<Integer> approximate_presence_count;
        private final Optional<WelcomeScreen> welcome_screen;
        private final NSFWLevel nsfw_level;
        private final Optional<Sticker[]> stickers;
        private final Boolean premium_progress_bar_enabled;
        @Nullable private final Snowflake safety_alerts_channel_id;
        @Nullable private final IncidentsData incidents_data;

    public Guild(JsonObject data) {
        super(data);
        icon_hash = data.has("icon_hash") ? (data.get("icon_hash").isJsonNull() ? null : Optional.of(data.get("icon_hash").getAsString())) : Optional.empty();
        splash = data.get("splash").isJsonNull() ? null : data.get("splash").getAsString();
        discovery_splash = data.get("discovery_splash").isJsonNull() ? null : data.get("discovery_splash").getAsString();
        owner = Optional.ofNullable(data.has("owner") ? data.get("owner").getAsBoolean() : null);
        owner_id = Snowflake.of(data.get("owner_id").getAsString());
        permissions = Optional.ofNullable(data.has("permissions") ? data.get("permissions").getAsString() : null);
        region = data.has("region") ? (data.get("region").isJsonNull() ? null : Optional.of(data.get("region").getAsString())) : Optional.empty();
        afk_channel_id = data.get("afk_channel_id").isJsonNull() ? null : Snowflake.of(data.get("afk_channel_id").getAsString());
        afk_timeout = data.get("afk_timeout").getAsInt();
        widget_enabled = Optional.ofNullable(data.has("widget_enabled") ? data.get("widget_enabled").getAsBoolean() : null);
        widget_channel_id = data.has("widget_channel_id") ? (data.get("widget_channel_id").isJsonNull() ? null : Optional.of(Snowflake.of(data.get("widget_channel_id").getAsString()))) : Optional.empty();
        verification_level = VerificationLevel.values()[data.get("verification_level").getAsInt()];
        default_message_notifications = MessageNotificationLevel.values()[data.get("default_message_notifications").getAsInt()];
        explicit_content_filter = ExplicitFilterLevel.values()[data.get("explicit_content_filter").getAsInt()];
        roles = data.getAsJsonArray("roles").asList().stream().map(roleObj -> new Role(roleObj.getAsJsonObject())).toArray(Role[]::new);
        emojis = data.getAsJsonArray("emojis").asList().stream().map(emojiObj -> new Emoji(emojiObj.getAsJsonObject())).toArray(Emoji[]::new);
        features = data.getAsJsonArray("features").asList().stream().map(feature -> GuildFeature.valueOf(feature.getAsString())).toArray(GuildFeature[]::new);
        mfa_level = MFALevel.values()[data.get("mfa_level").getAsInt()];
        application_id = data.get("application_id").isJsonNull() ? null : Snowflake.of(data.get("application_id").getAsString());
        system_channel_id = data.get("system_channel_id").isJsonNull() ? null : Snowflake.of(data.get("system_channel_id").getAsString());
        system_channel_flags = SystemChannelFlags.getFlagsFromInt(data.get("system_channel_flags").getAsInt());
        rules_channel_id = data.get("rules_channel_id").isJsonNull() ? null : Snowflake.of(data.get("rules_channel_id").getAsString());
        max_presences = data.has("max_presences") ? (data.get("max_presences").isJsonNull() ? null : Optional.of(data.get("max_presences").getAsInt())) : Optional.empty();
        max_members = Optional.ofNullable(data.has("max_members") ? data.get("max_members").getAsInt() : null);
        vanity_url_code = data.get("vanity_url_code").isJsonNull() ? null : data.get("vanity_url_code").getAsString();
        description = data.get("description").isJsonNull() ? null : data.get("description").getAsString();
        banner = data.get("banner").isJsonNull() ? null : data.get("banner").getAsString();
        premium_tier = PremiumTier.values()[data.get("premium_tier").getAsInt()];
        premium_subscription_count = Optional.ofNullable(data.has("premium_subscription_count") ? data.get("premium_subscription_count").getAsInt() : null);
        preferred_locale = data.get("preferred_locale").getAsString();
        public_updates_channel_id = data.get("public_updates_channel_id").isJsonNull() ? null : Snowflake.of(data.get("public_updates_channel_id").getAsString());
        max_video_channel_users = Optional.ofNullable(data.has("max_video_channel_users") ? data.get("max_video_channel_users").getAsInt() : null);
        max_stage_video_channel_users = Optional.ofNullable(data.has("max_stage_video_channel_users") ? data.get("max_stage_video_channel_users").getAsInt() : null);
        approximate_member_count = Optional.ofNullable(data.has("approximate_member_count") ? data.get("approximate_member_count").getAsInt() : null);
        approximate_presence_count = Optional.ofNullable(data.has("approximate_presence_count") ? data.get("approximate_presence_count").getAsInt() : null);
        welcome_screen = Optional.ofNullable(data.has("welcome_screen") ? new WelcomeScreen(data.getAsJsonObject("welcome_screen")) : null);
        nsfw_level = NSFWLevel.values()[data.get("nsfw_level").getAsInt()];
        stickers = Optional.ofNullable(data.has("stickers") ? data.getAsJsonArray("stickers").asList().stream().map(sticker -> new Sticker(sticker.getAsJsonObject())).toArray(Sticker[]::new) : null);
        premium_progress_bar_enabled = data.get("premium_progress_bar_enabled").getAsBoolean();
        safety_alerts_channel_id = data.get("safety_alerts_channel_id").isJsonNull() ? null : Snowflake.of(data.get("safety_alerts_channel_id").getAsString());
        incidents_data = data.get("incidents_data").isJsonNull() ? null : new IncidentsData(data.get("incidents_data").getAsJsonObject());
    }

    public JsonObject toJson() {
        JsonObject data = super.toJson();
        if(icon_hash == null) data.add("icon_hash", null);
        else icon_hash.ifPresent(ih -> data.addProperty("icon_hash", ih));
        data.addProperty("splash", splash);
        data.addProperty("discovery_splash",  discovery_splash);
        owner.ifPresent(o -> data.addProperty("owner", o));
        data.addProperty("owner_id", owner_id.toString());
        permissions.ifPresent(perms -> data.addProperty("permissions", perms));
        if(region == null) data.add("region", null);
        else region.ifPresent(r -> data.addProperty("region", r));
        data.addProperty("afk_channel_id", afk_channel_id == null ? null : afk_channel_id.toString());
        data.addProperty("afk_timeout", afk_timeout);
        widget_enabled.ifPresent(we -> data.addProperty("widget_enabled", we));
        if(widget_channel_id == null) data.add("widget_channel_id", null);
        else widget_channel_id.ifPresent(wci -> data.addProperty("widget_channel_id", wci.toString()));
        data.addProperty("verification_level", verification_level.ordinal());
        data.addProperty("default_message_notifications", default_message_notifications.ordinal());
        data.addProperty("explicit_content_filter", explicit_content_filter.ordinal());

        JsonArray rolesArr = new JsonArray(roles.length);
        Arrays.asList(roles).forEach(role -> rolesArr.add(role.toJson()));
        data.add("roles", rolesArr);

        JsonArray emojisArr = new JsonArray(emojis.length);
        Arrays.asList(emojis).forEach(emoji -> emojisArr.add(emoji.toJson()));
        data.add("emojis", emojisArr);

        JsonArray guildFeaturesArr = new JsonArray(features.length);
        Arrays.asList(features).forEach(feature -> guildFeaturesArr.add(feature.toString()));
        data.add("features", guildFeaturesArr);

        data.addProperty("mfa_level", mfa_level.ordinal());
        data.addProperty("application_id", application_id == null ? null : application_id.toString());
        data.addProperty("system_channel_id", system_channel_id == null ? null : system_channel_id.toString());
        data.addProperty("system_channel_flags", SystemChannelFlags.getFlagsAsInt(system_channel_flags));
        data.addProperty("rules_channel_id", rules_channel_id == null ? null : rules_channel_id.toString());
        if(max_presences == null) data.add("max_presences", null);
        else max_presences.ifPresent(mp -> data.addProperty("max_presences", mp));
        max_members.ifPresent(mm -> data.addProperty("max_members", mm));
        data.addProperty("vanity_url_code", vanity_url_code);
        data.addProperty("description", description);
        data.addProperty("banner", banner);
        data.addProperty("premium_tier", premium_tier.ordinal());
        premium_subscription_count.ifPresent(psc -> data.addProperty("premium_subscription_count", psc));
        data.addProperty("preferred_locale", preferred_locale);
        data.addProperty("public_updates_channel_id", public_updates_channel_id == null ? null : public_updates_channel_id.toString());
        max_video_channel_users.ifPresent(mvcu -> data.addProperty("max_video_channel_users", mvcu));
        max_stage_video_channel_users.ifPresent(msvcu -> data.addProperty("max_stage_video_channel_users", msvcu));
        approximate_member_count.ifPresent(amc -> data.addProperty("approximate_member_count", amc));
        approximate_presence_count.ifPresent(apc -> data.addProperty("approximate_presence_count", apc));
        welcome_screen.ifPresent(ws -> data.add("welcome_screen", ws.toJson()));
        data.addProperty("nsfw_level", nsfw_level.ordinal());

        stickers.ifPresent(stickers1 -> {
            JsonArray stickersArr = new JsonArray(stickers1.length);
            Arrays.asList(stickers1).forEach(sticker -> stickersArr.add(sticker.toJson()));
            data.add("stickers", stickersArr);
        });

        data.addProperty("premium_progress_bar_enabled", premium_progress_bar_enabled);
        data.addProperty("safety_alerts_channel_id", safety_alerts_channel_id == null ? null : safety_alerts_channel_id.toString());
        data.add("incidents_data", incidents_data == null ? null : incidents_data.toJson());

        return data;
    }

    public Channel lookupOrElseAdd(Channel channel) {
        return CHANNEL_REGISTRY.lookupOrElseAdd(channel);
    }

    public boolean remove(Channel channel) {
        return CHANNEL_REGISTRY.remove(channel);
    }

    public enum VerificationLevel {
        NONE,
        LOW,
        MEDIUM,
        HIGH,
        VERY_HIGH
    }

    public enum MessageNotificationLevel {
        ALL_MESSAGES,
        ONLY_MENTIONS
    }

    public enum ExplicitFilterLevel {
        DISABLED,
        MEMBERS_WITHOUT_ROLES,
        ALL_MEMBERS
    }

    public enum GuildFeature {
        ANIMATED_BANNER,
        ANIMATED_ICON,
        APPLICATION_COMMAND_PERMISSIONS_V2,
        AUTO_MODERATION,
        BANNER,
        COMMUNITY,
        CREATOR_MONETIZABLE_PROVISIONAL,
        CREATOR_STORE_PAGE,
        DEVELOPER_SUPPORT_SERVER,
        DISCOVERABLE,
        FEATURABLE,
        INVITES_DISABLED,
        INVITE_SPLASH,
        MEMBER_VERIFICATION_GATE_ENABLED,
        MORE_SOUNDBOARD,
        MORE_STICKERS,
        NEWS,
        PARTNERED,
        PREVIEW_ENABLED,
        RAID_ALERTS_DISABLED,
        ROLE_ICONS,
        ROLE_SUBSCRIPTIONS_AVAILABLE_FOR_PURCHASE,
        ROLE_SUBSCRIPTIONS_ENABLED,
        SOUNDBOARD,
        TICKETED_EVENTS_ENABLED,
        VANITY_URL,
        VERIFIED,
        VIP_REGIONS,
        WELCOME_SCREEN_ENABLED,
        GUILD_TAGS,
        ENHANCED_ROLE_COLORS
    }

    public enum MFALevel {
        NONE,
        ELEVATED
    }

    public enum SystemChannelFlags {
        SUPPRESS_JOIN_NOTIFICATIONS,
        SUPPRESS_PREMIUM_SUBSCRIPTIONS,
        SUPPRESS_GUILD_REMINDER_NOTIFICATIONS,
        SUPPRESS_JOIN_NOTIFICATION_REPLIES,
        SUPPRESS_ROLE_SUBSCRIPTION_PURCHASE_NOTIFICATIONS,
        SUPPRESS_ROLE_SUBSCRIPTION_PURCHASE_NOTIFICATION_REPLIES;

        public static int getFlagsAsInt(SystemChannelFlags... flags) {
            int flagInt = 0;
            for (SystemChannelFlags flag : flags) {
                flagInt += 1 << flag.ordinal();
            }
            return flagInt;
        }

        public static SystemChannelFlags[] getFlagsFromInt(int flags) {
            List<SystemChannelFlags> list = new LinkedList<>();
            for (SystemChannelFlags flag : SystemChannelFlags.values()) {
                if((flags >> flag.ordinal()) % 2 == 1)
                    list.add(flag);
            }
            return list.toArray(new SystemChannelFlags[0]);
        }
    }

    public enum PremiumTier {
        NONE,
        TIER_1,
        TIER_2,
        TIER_3
    }

    public enum NSFWLevel {
        DEFAULT,
        EXPLICIT,
        SAFE,
        AGE_RESTRICTED
    }
}
