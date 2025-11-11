package dev.shaythesquog.components.guilds;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.*;
import dev.shaythesquog.components.guilds.channels.Channel;
import dev.shaythesquog.components.guilds.channels.PartialChannel;
import dev.shaythesquog.components.users.User;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/resources/webhook#webhook-object-webhook-structure">Webhook Structure</a>
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class Webhook extends SnowflakeIdentifiable implements JsonAPIComponent {
    private final WebhookType type;
    @Nullable private final Optional<Snowflake> guild_id;
    @Nullable private final Snowflake channel_id;
    public final Optional<User> user;
    @Nullable private final String name;
    @Nullable private final String avatar;
    private final Optional<String> token;
    @Nullable private final Snowflake application_id;
    private final Optional<PartialGuild> source_guild;
    private final Optional<PartialChannel> source_channel;
    private final Optional<URL> url;

    public Webhook(JsonObject data, DiscordAPIAgent agent) {
        super(Snowflake.of(data.get("id").getAsString()));
        type = WebhookType.fromValue(data.get("type").getAsInt());
        guild_id = data.has("guild_id") ? (data.get("guild_id").isJsonNull() ? null : Optional.of(Snowflake.of(data.get("guild_id").getAsString()))) : Optional.empty();
        channel_id = data.get("channel_id").isJsonNull() ? null : Snowflake.of(data.get("channel_id").getAsString());
        user = Optional.ofNullable(data.has("user") ? new User(data.get("user").getAsJsonObject()) : null);
        name = data.get("name").isJsonNull() ? null : data.get("name").getAsString();
        avatar = data.get("avatar").isJsonNull() ? null : data.get("avatar").getAsString();
        token = Optional.ofNullable(data.has("token") ? data.get("token").getAsString() : null);
        application_id = data.get("application_id").isJsonNull() ? null : Snowflake.of(data.get("application_id").getAsString());

        if(data.has("source_guild")) {
            Snowflake sourceGuildSnowflake = Snowflake.of(data.getAsJsonObject("source_guild").get("id").getAsString());
            if(agent.GUILD_REGISTRY.has(sourceGuildSnowflake))
                source_guild = Optional.of(agent.GUILD_REGISTRY.get(sourceGuildSnowflake));
            else
                source_guild = Optional.of(new PartialGuild(data.getAsJsonObject("source_guild")));
        } else {
            source_guild = Optional.empty();
        }

        if(data.has("source_channel")) {
            source_channel = Optional.of(new PartialChannel(data.getAsJsonObject("source_channel")));
        } else {
            source_channel = Optional.empty();
        }

        Optional<URL> temp;
        try {
            temp = Optional.ofNullable(data.has("url") ? new URI(data.get("url").getAsString()).toURL() : null);
        } catch (MalformedURLException | URISyntaxException e) {
            temp = Optional.empty();
            // TODO some logging here
        }
        url = temp;
    }

    @Override
    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("id", id.toString());
        data.addProperty("type", type.value);
        Util.addNullableOptionalIfPresent(guild_id, "guild_id", data);
        data.addProperty("channel_id", channel_id == null ? null : channel_id.toString());
        user.ifPresent(u -> data.add("user", u.toJson()));
        data.addProperty("name", name);
        data.addProperty("avatar", avatar);
        token.ifPresent(tok -> data.addProperty("token", tok));
        data.addProperty("application_id", application_id == null ? null : application_id.toString());
        source_guild.ifPresent(sg -> data.add("source_guild", sg.toJson()));
        source_channel.ifPresent(sc -> data.add("source_channel", sc.toJson()));
        url.ifPresent(u -> data.addProperty("url", u.toString()));
        return data;
    }

    public enum WebhookType {
        Incoming(1),
        Channel_Follower(2),
        Application(3);

        private final int value;

        WebhookType(int value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
        public int getValue() {return value;}

        public static WebhookType fromValue(int value) {
            return switch(value) {
                case 1 -> Incoming;
                case 2 -> Channel_Follower;
                case 3 -> Application;
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
