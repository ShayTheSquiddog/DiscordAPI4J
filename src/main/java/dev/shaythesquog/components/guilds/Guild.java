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

/**
 * @see <a href="https://discord.com/developers/docs/resources/guild#guild-object">Guild</a>
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class Guild implements JsonAPIComponent {
        public final Snowflake id;
        private final String name;
        @Nullable private final String icon;
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
        id = new Snowflake(data.get("id").getAsString());
        name = data.get("name").getAsString();
        icon = data.get("icon").isJsonNull() ? null : data.get("icon").getAsString();
        icon_hash = data.has("icon_hash") ? (data.get("icon_hash").isJsonNull() ? null : Optional.of(data.get("icon_hash").getAsString())) : Optional.empty();
        splash = data.get("splash").isJsonNull() ? null : data.get("splash").getAsString();
        discovery_splash = data.get("discovery_splash").isJsonNull() ? null : data.get("discovery_splash").getAsString();
        owner = Optional.ofNullable(data.has("owner") ? data.get("owner").getAsBoolean() : null);
        owner_id = new Snowflake(data.get("owner_id").getAsString());
        permissions = Optional.ofNullable(data.has("permissions") ? data.get("permissions").getAsString() : null);
        region = data.has("region") ? (data.get("region").isJsonNull() ? null : Optional.of(data.get("region").getAsString())) : Optional.empty();
        afk_channel_id = data.get("afk_channel_id").isJsonNull() ? null : new Snowflake(data.get("afk_channel_id").getAsString());
        afk_timeout = data.get("afk_timeout").getAsInt();
        widget_enabled = Optional.ofNullable(data.has("widget_enabled") ? data.get("widget_enabled").getAsBoolean() : null);
        widget_channel_id = data.has("widget_channel_id") ? (data.get("widget_channel_id").isJsonNull() ? null : Optional.of(new Snowflake(data.get("widget_channel_id").getAsString()))) : Optional.empty();
        verification_level = VerificationLevel.values()[data.get("verification_level").getAsInt()];
        default_message_notifications = MessageNotificationLevel.values()[data.get("default_message_notifications").getAsInt()];
        explicit_content_filter = ExplicitFilterLevel.values()[data.get("explicit_content_filter").getAsInt()];
        roles = data.getAsJsonArray("roles").asList().stream().map(roleObj -> new Role(roleObj.getAsJsonObject())).toArray(Role[]::new);
        emojis = data.getAsJsonArray("emojis").asList().stream().map(emojiObj -> new Emoji(emojiObj.getAsJsonObject())).toArray(Emoji[]::new);
        features = data.getAsJsonArray("features").asList().stream().map(feature -> GuildFeature.valueOf(feature.getAsString())).toArray(GuildFeature[]::new);
        mfa_level = MFALevel.values()[data.get("mfa_level").getAsInt()];
        application_id = data.get("application_id").isJsonNull() ? null : new Snowflake(data.get("application_id").getAsString());
        system_channel_id = data.get("system_channel_id").isJsonNull() ? null : new Snowflake(data.get("system_channel_id").getAsString());
        system_channel_flags = SystemChannelFlags.getFlagsFromInt(data.get("system_channel_flags").getAsInt());
        rules_channel_id = data.get("rules_channel_id").isJsonNull() ? null : new Snowflake(data.get("rules_channel_id").getAsString());
        max_presences = data.has("max_presences") ? (data.get("max_presences").isJsonNull() ? null : Optional.of(data.get("max_presences").getAsInt())) : Optional.empty();
        max_members = Optional.ofNullable(data.has("max_members") ? data.get("max_members").getAsInt() : null);
        vanity_url_code = data.get("vanity_url_code").isJsonNull() ? null : data.get("vanity_url_code").getAsString();
        description = data.get("description").isJsonNull() ? null : data.get("description").getAsString();
        banner = data.get("banner").isJsonNull() ? null : data.get("banner").getAsString();
        premium_tier = PremiumTier.values()[data.get("premium_tier").getAsInt()];
        premium_subscription_count = Optional.ofNullable(data.has("premium_subscription_count") ? data.get("premium_subscription_count").getAsInt() : null);
        preferred_locale = data.get("preferred_locale").getAsString();
        public_updates_channel_id = data.get("public_updates_channel_id").isJsonNull() ? null : new Snowflake(data.get("public_updates_channel_id").getAsString());
        max_video_channel_users = Optional.ofNullable(data.has("max_video_channel_users") ? data.get("max_video_channel_users").getAsInt() : null);
        max_stage_video_channel_users = Optional.ofNullable(data.has("max_stage_video_channel_users") ? data.get("max_stage_video_channel_users").getAsInt() : null);
        approximate_member_count = Optional.ofNullable(data.has("approximate_member_count") ? data.get("approximate_member_count").getAsInt() : null);
        approximate_presence_count = Optional.ofNullable(data.has("approximate_presence_count") ? data.get("approximate_presence_count").getAsInt() : null);
        welcome_screen = Optional.ofNullable(data.has("welcome_screen") ? new WelcomeScreen(data.getAsJsonObject("welcome_screen")) : null);
        nsfw_level = NSFWLevel.values()[data.get("nsfw_level").getAsInt()];
        stickers = Optional.ofNullable(data.has("stickers") ? data.getAsJsonArray("stickers").asList().stream().map(sticker -> new Sticker(sticker.getAsJsonObject())).toArray(Sticker[]::new) : null);
        premium_progress_bar_enabled = data.get("premium_progress_bar_enabled").getAsBoolean();
        safety_alerts_channel_id = data.get("safety_alerts_channel_id").isJsonNull() ? null : new Snowflake(data.get("safety_alerts_channel_id").getAsString());
        incidents_data = data.get("incidents_data").isJsonNull() ? null : new IncidentsData(data.get("incidents_data").getAsJsonObject());
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        data.addProperty("name", name);
        data.addProperty("icon", icon);
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

    public GuildMember newMember(JsonObject memberJson) {
        return new GuildMember(memberJson);
    }

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

        public GuildMember(JsonObject data) {
            user = Optional.ofNullable(data.has("user") ? new User(data.get("user").getAsJsonObject()) : null);
            nick = data.has("nick") ? (data.get("nick").isJsonNull() ? null : Optional.of(data.get("nick").getAsString())) : Optional.empty();
            avatar = data.has("avatar") ? (data.get("avatar").isJsonNull() ? null : Optional.of(data.get("avatar").getAsString())) : Optional.empty();
            banner = data.has("banner") ? (data.get("banner").isJsonNull() ? null : Optional.of(data.get("banner").getAsString())) : Optional.empty();
            roles = data.get("roles").getAsJsonArray().asList().stream().map(roleSF -> new Snowflake(roleSF.getAsString())).toArray(Snowflake[]::new);
            joined_at = data.has("joined_at") ? Instant.parse(data.get("joined_at").getAsString()) : null;
            premium_since = data.has("premium_since") ? (data.get("premium_since").isJsonNull() ? null : Optional.of(Instant.parse(data.get("premium_since").getAsString()))) : Optional.empty();
            deaf = data.get("deaf").getAsBoolean();
            mute = data.get("mute").getAsBoolean();
            flags = GuildMemberFlags.getFlagsFromInt(data.get("flags").getAsInt());
            pending = Optional.ofNullable(data.has("pending") ? data.get("pending").getAsBoolean() : null);
            permissions = Optional.ofNullable(data.has("permissions") ? data.get("permissions").getAsString() : null);
            communication_disabled_until = data.has("communication_disabled_until") ? (data.get("communication_disabled_until").isJsonNull() ? null : Optional.of(Instant.parse(data.get("communication_disabled_until").getAsString()))) : Optional.empty();
            avatar_decoration_data = data.has("avatar_decoration_data") ? (data.get("avatar_decoration_data").isJsonNull() ? null : Optional.of(new AvatarDecorationData(data.getAsJsonObject("avatar_decoration_data")))) : Optional.empty();
        }

        @Override
        public JsonObject toJson() {
            JsonObject data = new JsonObject();
            user.ifPresent(userObj -> data.add("user", userObj.toJson()));
            if(nick == null) data.add("nick", null);
            else nick.ifPresent(nickObj -> data.addProperty("nick", nickObj));
            if(avatar == null) data.add("avatar", null);
            else avatar.ifPresent(avatarObj -> data.addProperty("avatar", avatarObj));
            if(banner == null) data.add("banner", null);
            else banner.ifPresent(bannerObj -> data.addProperty("banner", bannerObj));

            JsonArray rolesJson = new JsonArray();
            for(Snowflake roleSF : roles)
                rolesJson.add(roleSF.toString());
            data.add("roles", rolesJson);

            data.addProperty("joined_at", joined_at == null ? null : joined_at.toString());
            if(premium_since == null) data.add("premium_since", null);
            else premium_since.ifPresent(premiumInstant -> data.addProperty("premium_since", premiumInstant.toString()));
            data.addProperty("deaf", deaf);
            data.addProperty("mute", mute);
            data.addProperty("flags", GuildMemberFlags.getFlagsAsInt(flags));
            pending.ifPresent(pendingObj -> data.addProperty("pending", pendingObj));
            permissions.ifPresent(permissionsObj -> data.addProperty("permissions", permissionsObj));
            if(communication_disabled_until == null) data.add("communication_disabled_until", null);
            else communication_disabled_until.ifPresent(cduInstant -> data.addProperty("communication_disabled_until", cduInstant.toString()));
            if(avatar_decoration_data == null) data.add("avatar_decoration_data", null);
            else avatar_decoration_data.ifPresent(addObj -> data.add("avatar_decoration_data", addObj.toJson()));

            return data;
        }

        public String avatarURL() {
            if(user.isEmpty() || avatar == null || avatar.isEmpty())
                return user.map(User::avatarURL).orElse(null);
            StringBuilder avatarURL = new StringBuilder(String.format("https://cdn.discordapp.com/guilds/%s/users/%s/avatars/%s", id.toString(), user.get().getId().toString(), avatar.get()));
            if(avatar.get().startsWith("a_"))
                avatarURL.append(".gif");
            else
                avatarURL.append(".png");
            return avatarURL.toString();
        }

        @SuppressWarnings({"Duplicates", "unused"})
        public String avatarURL(int size) {
            final int[] validSizes = new int[] {16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
            Arrays.sort(validSizes);
            if(Arrays.binarySearch(validSizes, size) < 0)
                throw new IllegalArgumentException("Invalid size: " + size);
            return avatarURL() + "?size=" + size;
        }

        @SuppressWarnings("unused")
        public String getDisplayName() {
            if(nick != null && nick.isPresent())
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
                for(GuildMemberFlags flag : GuildMemberFlags.values()) {
                    if((flagsInt >> flag.value) % 2 == 1) {
                        result.add(flag);
                    }
                }
                return result.toArray(new GuildMemberFlags[0]);
            }
        }
    }
}
