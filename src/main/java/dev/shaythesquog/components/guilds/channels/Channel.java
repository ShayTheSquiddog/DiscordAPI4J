package dev.shaythesquog.components.guilds.channels;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.shaythesquog.components.JsonAPIComponent;
import dev.shaythesquog.components.Snowflake;
import dev.shaythesquog.components.SnowflakeIdentifiable;
import dev.shaythesquog.components.Util;
import dev.shaythesquog.components.guilds.GuildMember;
import dev.shaythesquog.components.users.User;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/resources/channel#channel-object">Channel Object</a>
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class Channel extends PartialChannel implements JsonAPIComponent {
    private final ChannelType type;
    private final Optional<Snowflake> guild_id;
    private final Optional<Integer> position;
    private final Optional<Overwrite[]> permission_overwrites;
    @Nullable private final Optional<String> topic;
    private final Optional<Boolean> nsfw;
    @Nullable private final Optional<Snowflake> last_message_id;
    private final Optional<Integer> bitrate;
    private final Optional<Integer> user_limit;
    private final Optional<Integer> rate_limit_per_user;
    private final Optional<User[]> recipients;
    @Nullable private final Optional<String> icon;
    private final Optional<Snowflake> owner_id;
    private final Optional<Snowflake> application_id;
    private final Optional<Boolean> managed;
    @Nullable private final Optional<Snowflake> parent_id;
    @Nullable private final Optional<Instant> last_pin_timestamp;
    @Nullable private final Optional<String> rtc_region;
    private final Optional<VideoQualityMode> video_quality_mode;
    private final Optional<Integer> message_count;
    private final Optional<ThreadMetadata> thread_metadata;
    private final Optional<ThreadMember> new_member;
    private final Optional<Integer> default_auto_archive_duration;
    private final Optional<String> permissions;
    private final Optional<ChannelFlag[]> flags;
    private final Optional<Tag[]> available_tags;
    private final Optional<Snowflake[]> applied_tags;
    @Nullable private final Optional<DefaultReaction> default_reaction_emoji;
    private final Optional<Integer> default_thread_rate_limit_per_user;
    @Nullable private final Optional<SortOrder> default_sort_order;
    private final Optional<ForumLayoutType> default_forum_layout;

    public Channel(JsonObject data) {
        super(data);
        type = ChannelType.fromId(data.get("type").getAsInt());
        guild_id = Optional.ofNullable(data.has("guild_id") ? Snowflake.of(data.get("guild_id").getAsString()) : null);
        position = Optional.ofNullable(data.has("position") ? data.getAsInt() : null);

        if(data.has("permission_overwrites")) {
            JsonArray overwriteObjs = data.getAsJsonArray("permission_overwrites");
            permission_overwrites = Optional.of(new Overwrite[overwriteObjs.size()]);
            for(int i = 0; i < overwriteObjs.size(); i++) {
                permission_overwrites.get()[i] = new Overwrite(overwriteObjs.get(i).getAsJsonObject());
            }
        } else {
            permission_overwrites = Optional.empty();
        }

        topic = data.has("topic") ? (data.get("topic").isJsonNull() ? null : Optional.of(data.get("topic").getAsString())) : Optional.empty();
        nsfw = Optional.ofNullable(data.has("nsfw") ? data.getAsBoolean() : null);
        last_message_id = data.has("last_message_id") ? (data.get("last_message_id").isJsonNull() ? null : Optional.of(Snowflake.of(data.get("last_message_id").getAsString()))) : Optional.empty();
        bitrate = Optional.ofNullable(data.has("bitrate") ? data.getAsInt() : null);
        user_limit = Optional.ofNullable(data.has("user_limit") ? data.getAsInt() : null);
        rate_limit_per_user = Optional.ofNullable(data.has("rate_limit_per_user") ? data.getAsInt() : null);

        if(data.has("recipients")) {
            JsonArray recipientObjs = data.getAsJsonArray("recipients");
            recipients = Optional.of(new User[recipientObjs.size()]);
            for(int i = 0; i < recipientObjs.size(); i++) {
                recipients.get()[i] = new User(recipientObjs.get(i).getAsJsonObject());
            }
        } else {
            recipients = Optional.empty();
        }

        icon = data.has("icon") ? (data.get("icon").isJsonNull() ? null : Optional.ofNullable(data.get("icon").getAsString())) : null;
        owner_id = Optional.ofNullable(data.has("owner_id") ? Snowflake.of(data.get("owner_id").getAsString()) : null);
        application_id = Optional.ofNullable(data.has("application_id") ? Snowflake.of(data.get("application_id").getAsString()) : null);
        managed = Optional.ofNullable(data.has("managed") ? data.getAsBoolean() : null);
        parent_id = data.has("parent_id") ? (data.get("parent_id").isJsonNull() ? null : Optional.of(Snowflake.of(data.get("parent_id").getAsString()))) : Optional.empty();
        last_pin_timestamp = data.has("last_pin_timestamp") ? (data.get("last_pin_timestamp").isJsonNull() ? null : Optional.of(Instant.parse(data.get("last_pin_timestamp").getAsString()))) : Optional.empty();
        rtc_region = data.has("rtc_region") ? (data.get("rtc_region").isJsonNull() ? null : Optional.of(data.get("rtc_region").getAsString())) : Optional.empty();
        video_quality_mode = Optional.ofNullable(data.has("video_quality_mode") ? VideoQualityMode.fromValue(data.get("video_quality_mode").getAsInt()) : null);
        message_count = Optional.ofNullable(data.has("message_count") ? data.get("message_count").getAsInt() : null);
        thread_metadata = Optional.ofNullable(data.has("thread_metadata") ? new ThreadMetadata(data.getAsJsonObject("thread_metadata")): null);
        new_member = Optional.ofNullable(data.has("new_member") ? new ThreadMember(data.getAsJsonObject("new_member")) : null);
        default_auto_archive_duration = Optional.ofNullable(data.has("default_auto_archive_duration") ? data.get("default_auto_archive_duration").getAsInt() : null);
        permissions = Optional.ofNullable(data.has("permissions") ? data.get("permissions").getAsString() : null);
        flags = Optional.ofNullable(data.has("flags") ? ChannelFlag.getFlagsFromInt(data.get("flags").getAsInt()) : null);

        if(data.has("available_tags")) {
            JsonArray tagObjs = data.getAsJsonArray("available_tags");
            available_tags = Optional.of(new Tag[tagObjs.size()]);
            for(int i = 0; i < tagObjs.size(); i++) {
                available_tags.get()[i] = new Tag(tagObjs.get(i).getAsJsonObject());
            }
        } else {
            available_tags = Optional.empty();
        }

        if(data.has("applied_tags")) {
            JsonArray snowflakes = data.getAsJsonArray("applied_tags");
            applied_tags = Optional.of(new Snowflake[snowflakes.size()]);
            for(int i = 0; i < snowflakes.size(); i++) {
                applied_tags.get()[i] = Snowflake.of(snowflakes.get(i).getAsString());
            }
        } else {
            applied_tags = Optional.empty();
        }

        default_reaction_emoji = data.has("default_reaction_emoji") ? (data.get("default_reaction_emoji").isJsonNull() ? null : Optional.of(new DefaultReaction(data.get("default_reaction_emoji").getAsJsonObject()))) : Optional.empty();
        default_thread_rate_limit_per_user = Optional.ofNullable(data.has("default_thread_rate_limit_per_user") ? data.getAsInt() : null);
        default_sort_order = data.has("default_sort_order") ? (data.get("default_sort_order").isJsonNull() ? null : Optional.of(SortOrder.values()[data.get("default_sort_order").getAsInt()])) : Optional.empty();
        default_forum_layout = Optional.ofNullable(data.has("default_forum_layout") ? ForumLayoutType.values()[data.get("default_forum_layout").getAsInt()] : null);
    }

    @Override
    public JsonObject toJson() {
        JsonObject data = super.toJson();
        data.addProperty("type", type.id);
        guild_id.ifPresent(gid -> data.addProperty("guild_id", gid.toString()));
        position.ifPresent(p -> data.addProperty("position", p));
        permission_overwrites.ifPresent(overwrites -> {
            JsonArray arr = new JsonArray(overwrites.length);
            for(Overwrite overwrite : overwrites) {
                arr.add(overwrite.toJson());
            }
            data.add("permission_overwrites", arr);
        });
        Util.addNullableOptionalIfPresent(topic, "topic", data);
        nsfw.ifPresent(n -> data.addProperty("nsfw", n));
        Util.addNullableOptionalIfPresent(last_message_id, "last_message_id", data);
        bitrate.ifPresent(b -> data.addProperty("bitrate", b));
        user_limit.ifPresent(limit -> data.addProperty("user_limit", limit));
        rate_limit_per_user.ifPresent(limit -> data.addProperty("rate_limit_per_user", limit));
        recipients.ifPresent(receivers -> {
            JsonArray arr = new JsonArray(receivers.length);
            for(User receiver : receivers) {
                arr.add(receiver.toJson());
            }
            data.add("recipients", arr);
        });
        Util.addNullableOptionalIfPresent(icon, "icon", data);
        owner_id.ifPresent(oid -> data.addProperty("owner_id", oid.toString()));
        application_id.ifPresent(appid -> data.addProperty("application_id", appid.toString()));
        managed.ifPresent(mgd -> data.addProperty("managed", mgd));
        Util.addNullableOptionalIfPresent(parent_id, "parent_id", data);
        Util.addNullableOptionalIfPresent(last_pin_timestamp, "last_pin_timestamp", data);
        Util.addNullableOptionalIfPresent(rtc_region, "rtc_region", data);
        video_quality_mode.ifPresent(vqm -> data.addProperty("video_quality_mode", vqm.value));
        message_count.ifPresent(mc -> data.addProperty("message_count", mc));
        thread_metadata.ifPresent(tmd -> data.add("thread_metadata", tmd.toJson()));
        new_member.ifPresent(nm -> data.add("new_member", nm.toJson()));
        default_auto_archive_duration.ifPresent(daad -> data.addProperty("default_auto_archive_duration", daad));
        permissions.ifPresent(perms -> data.addProperty("permissions", perms));
        flags.ifPresent(f -> data.addProperty("flags", ChannelFlag.getFlagsAsInt(f)));
        available_tags.ifPresent(tags -> {
            JsonArray tagArr = new JsonArray(tags.length);
            for(Tag tag : tags) {
                tagArr.add(tag.toJson());
            }
            data.add("available_tags", tagArr);
        });
        applied_tags.ifPresent(tagsSF -> {
            JsonArray sfArr = new JsonArray(tagsSF.length);
            for(Snowflake tagSF : tagsSF) {
                sfArr.add(tagSF.toString());
            }
            data.add("applied_tags", sfArr);
        });
        Util.addNullableOptionalIfPresent(default_reaction_emoji, "default_reaction_emoji", data);
        default_thread_rate_limit_per_user.ifPresent(dtrlpu -> data.addProperty("default_thread_rate_limit_per_user", dtrlpu));
        if(default_sort_order == null) data.add("default_sort_order", null);
        else default_sort_order.ifPresent(dso -> data.addProperty("default_sort_order", dso.ordinal()));
        default_forum_layout.ifPresent(dfl -> data.addProperty("default_forum_layout", dfl.ordinal()));

        return data;
    }

    public enum ChannelType{
        GUILD_TEXT(0),
        DM(1),
        GUILD_VOICE(2),
        GROUP_DM(3),
        GUILD_CATEGORY(4),
        GUILD_ANNOUNCEMENT(5),
        ANNOUNCEMENT_THREAD(10),
        PUBLIC_THREAD(11),
        PRIVATE_THREAD(12),
        GUILD_STAGE_VOICE(13),
        GUILD_DIRECTORY(14),
        GUILD_FORUM(15),
        GUILD_MEDIA(16);

        public final int id;

        ChannelType(int id){ this.id = id; }

        public static ChannelType fromId(int id){
            return switch(id) {
                case 0 -> GUILD_TEXT;
                case 1 -> DM;
                case 2 -> GUILD_VOICE;
                case 3 -> GROUP_DM;
                case 4 -> GUILD_CATEGORY;
                case 5 -> GUILD_ANNOUNCEMENT;
                case 10 -> ANNOUNCEMENT_THREAD;
                case 11 -> PUBLIC_THREAD;
                case 12 -> PRIVATE_THREAD;
                case 13 -> GUILD_STAGE_VOICE;
                case 14 -> GUILD_DIRECTORY;
                case 15 -> GUILD_FORUM;
                case 16 -> GUILD_MEDIA;
                default -> null;
            };
        }
    }

    public enum VideoQualityMode{
        AUTO(1),
        FULL(2);

        public final int value;

        VideoQualityMode(int value){ this.value = value; }

        public static VideoQualityMode fromValue(int value){
            return switch(value) {
                case 1 -> AUTO;
                case 2 -> FULL;
                default -> null;
            };
        }
    }

    public enum ChannelFlag{
        PINNED(1),
        REQUIRE_TAG(4),
        HIDE_MEDIA_DOWNLOAD_OPTIONS(15);

        public final int value;
        ChannelFlag(int value){ this.value = value; }

        @SuppressWarnings("unused")
        public static ChannelFlag fromValue(int value){
            return switch(value) {
                case 1 -> PINNED;
                case 4 -> REQUIRE_TAG;
                case 15 -> HIDE_MEDIA_DOWNLOAD_OPTIONS;
                default -> null;
            };
        }

        public static ChannelFlag[] getFlagsFromInt(int flagVal) {
            List<ChannelFlag> flags = new LinkedList<>();
            for(ChannelFlag flag : ChannelFlag.values()) {
                if((flagVal >> flag.value) % 2 == 1) {
                    flags.add(flag);
                }
            }
            return flags.toArray(new ChannelFlag[0]);
        }

        public static int getFlagsAsInt(ChannelFlag... flags) {
            int result = 0;
            for(ChannelFlag flag : flags) {
                result += 1 << flag.value;
            }
            return result;
        }
    }

    public enum SortOrder{
        LATEST_ACTIVITY,
        CREATION_DATE
    }

    public enum ForumLayoutType{
        NOT_SET,
        LIST_VIEW,
        GALLERY_VIEW
    }

    public static class ThreadMetadata implements JsonAPIComponent {
        private final boolean archived;
        private final int auto_archive_duration;
        private final Instant archive_timestamp;
        private final boolean locked;
        private final Optional<Boolean> invitable;
        @Nullable private final Optional<Instant> create_timestamp;

        public ThreadMetadata(JsonObject data) {
            archived = data.get("archived").getAsBoolean();
            auto_archive_duration = data.get("auto_archive_duration").getAsInt();
            archive_timestamp = Instant.parse(data.get("archive_timestamp").getAsString());
            locked = data.get("locked").getAsBoolean();
            invitable = Optional.ofNullable(data.has("invitable") ? data.get("invitable").getAsBoolean() : null);
            create_timestamp = data.has("create_timestamp") ? (data.get("create_timestamp").isJsonNull() ? null : Optional.of(Instant.parse(data.get("create_timestamp").getAsString()))) : Optional.empty();
        }

        @Override
        public JsonObject toJson() {
            JsonObject data = new JsonObject();
            data.addProperty("archived", archived);
            data.addProperty("auto_archive_duration", auto_archive_duration);
            data.addProperty("archive_timestamp", archive_timestamp.toString());
            data.addProperty("locked", locked);
            invitable.ifPresent(inv -> data.addProperty("invitable", inv));
            if(create_timestamp == null) data.add("create_timestamp", null);
            else create_timestamp.ifPresent(inst -> data.addProperty("create_timestamp", inst.toString()));
            return data;
        }
    }
    
    public class ThreadMember implements JsonAPIComponent {
        private final Optional<Snowflake> id;
        private final Optional<Snowflake> user_id;
        private final Instant join_timestamp;
        private final int flags;
        private final Optional<GuildMember> member;

        public ThreadMember(JsonObject data) {
            id = Optional.ofNullable(data.has("id") ? Snowflake.of(data.get("id").getAsString()) : null);
            user_id = Optional.ofNullable(data.has("user_id") ? Snowflake.of(data.get("user_id").getAsString()) : null);
            join_timestamp = Instant.parse(data.get("join_timestamp").getAsString());
            flags = data.get("flags").getAsInt();
            member = Optional.ofNullable(data.has("member") ? new GuildMember(data.getAsJsonObject("member"), guild_id.orElse(null)) : null);
        }

        public JsonObject toJson() {
            JsonObject data = new JsonObject();
            id.ifPresent(i -> data.addProperty("id", i.toString()));
            user_id.ifPresent(i -> data.addProperty("user_id", i.toString()));
            data.addProperty("join_timestamp", join_timestamp.toString());
            data.addProperty("flags", flags);
            member.ifPresent(m -> data.add("member", m.toJson()));
            return data;
        }
    }
}
