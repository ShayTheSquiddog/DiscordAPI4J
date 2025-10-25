package dev.shaythesquog.components.users;

import com.google.gson.JsonObject;
import dev.shaythesquog.components.AbstractAPIComponent;
import dev.shaythesquog.components.Snowflake;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @see <a href="https://discord.com/developers/docs/resources/user#user-object">Users</a>
 */
// These two behaviors are intended, as this helps code differentiate between actual values that were sent as null by
// the api, and values that just were not sent at all. Values tagged with @Nullable are values that can be sent as null
// objects by the api, and Optional fields are values that may or may not end up being sent by the API
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
public class User extends AbstractAPIComponent {
   private final Snowflake id;
   private final String username;
   private final String discriminator;
   @Nullable private final String global_name;
   @Nullable private final String avatar;
   private final Optional<Boolean> bot;
   private final Optional<Boolean> system;
   private final Optional<Boolean> mfa_enabled;
   @Nullable private final Optional<String> banner;
   @Nullable private final Optional<Integer> accent_color;
   private final Optional<String> locale;
   private final Optional<Boolean> verified;
   @Nullable private final Optional<String> email;
   private final Optional<Flags[]> flags;
   private final Optional<NitroType> premium_type;
   private final Optional<Flags[]> public_flags;
   @Nullable private final Optional<AvatarDecorationData> avatar_decoration_data;
   @Nullable private final Optional<Collectibles> collectibles;
   @Nullable private final Optional<UserPrimaryGuild> primary_guild;

   public User(JsonObject data) {
       id = new Snowflake(data.get("id").getAsString());
       username = data.get("username").getAsString();
       discriminator = data.get("discriminator").getAsString();
       global_name = data.get("global_name").isJsonNull() ? null : data.get("global_name").getAsString();
       avatar = data.get("avatar").isJsonNull() ? null : data.get("avatar").getAsString();
       bot = Optional.ofNullable(data.has("bot") ? data.get("bot").getAsBoolean() : null);
       system = Optional.ofNullable(data.has("system") ? data.get("system").getAsBoolean() : null);
       mfa_enabled = Optional.ofNullable(data.has("mfa_enabled") ? data.get("mfa_enabled").getAsBoolean() : null);
       banner = data.has("banner") ? (data.get("banner").isJsonNull() ? null : Optional.of(data.get("banner").getAsString())) : Optional.empty();
       accent_color = data.has("accent_color") ? (data.get("accent_color").isJsonNull() ? null : Optional.of(data.get("accent_color").getAsInt())) : Optional.empty();
       locale = Optional.ofNullable(data.has("locale") ? data.get("locale").getAsString() : null);
       verified = Optional.ofNullable(data.has("verified") ? data.get("verified").getAsBoolean() : null);
       email = data.has("email") ? (data.get("email").isJsonNull() ? null : Optional.of(data.get("email").getAsString())) : Optional.empty();
       flags = Optional.ofNullable(data.has("flags") ? Flags.getFlagsFromInt(data.get("flags").getAsInt()) : null);
       premium_type = Optional.ofNullable(data.has("premium_type") ? NitroType.valueOfOrdinal(data.get("premium_type").getAsInt()) : null);
       public_flags = Optional.ofNullable(data.has("public_flags") ? Flags.getFlagsFromInt(data.get("public_flags").getAsInt()) : null);
       avatar_decoration_data = data.has("avatar_decoration_data") ? (data.get("avatar_decoration_data").isJsonNull() ? null : Optional.of(new AvatarDecorationData(data.get("avatar_decoration_data").getAsJsonObject()))) : Optional.empty();
       collectibles = data.has("collectibles") ? (data.get("collectibles").isJsonNull() ? null : Optional.of(new Collectibles(data.get("collectibles").getAsJsonObject()))) : Optional.empty();
       primary_guild = data.has("primary_guild") ? (data.get("primary_guild").isJsonNull() ? null : Optional.of(new UserPrimaryGuild(data.get("primary_guild").getAsJsonObject()))) : Optional.empty();
   }

   public JsonObject toJson() {
       JsonObject data = new JsonObject();
       data.addProperty("id", id.toString());
       data.addProperty("username", username);
       data.addProperty("discriminator", discriminator);
       data.addProperty("global_name", global_name);
       data.addProperty("avatar", avatar);
       bot.ifPresent(bot -> data.addProperty("bot", bot.toString()));
       system.ifPresent(system -> data.addProperty("system", system.toString()));
       mfa_enabled.ifPresent(mfa -> data.addProperty("mfa_enabled", mfa));
       if(banner == null) data.add("banner", null);
       else banner.ifPresent(bnr -> data.addProperty("banner", bnr));
       if(accent_color == null) data.add("accent_color", null);
       else accent_color.ifPresent(accent -> data.addProperty("accent_color", accent));
       locale.ifPresent(loc -> data.addProperty("locale", loc));
       verified.ifPresent(ver -> data.addProperty("verified", ver));
       if(email == null) data.add("email", null);
       else email.ifPresent(mail -> data.addProperty("email", mail));
       flags.ifPresent(fla -> data.addProperty("flags", Flags.getFlagsAsInt(fla)));
       premium_type.ifPresent(prem -> data.addProperty("premium_type", prem.ordinal()));
       public_flags.ifPresent(fla -> data.addProperty("public_flags", Flags.getFlagsAsInt(fla)));
       if(avatar_decoration_data == null) data.add("avatar_decoration_data", null);
       else avatar_decoration_data.ifPresent(aviDec -> data.add("avatar_decoration_data", aviDec.toJson()));
       if(collectibles == null) data.add("collectibles", null);
       else collectibles.ifPresent(collec -> data.add("collectibles", collec.toJson()));
       if(primary_guild == null) data.add("primary_guild", null);
       else primary_guild.ifPresent(guild -> data.add("primary_guild", guild.toJson()));
       return data;
   }

   public enum Flags{
       STAFF(0),
       PARTNER(1),
       HYPESQUAD(2),
       BUG_HUNTER_LEVEL_1(3),
       HYPESQUAD_ONLINE_HOUSE_1(6),
       HYPESQUAD_ONLINE_HOUSE_2(7),
       HYPESQUAD_ONLINE_HOUSE_3(8),
       PREMIUM_EARLY_SUPPORTER(9),
       TEAM_PSEUDO_USER(10),
       BUG_HUNTER_LEVEL_2(14),
       VERIFIED_BOT(16),
       VERIFIED_DEVELOPER(17),
       CERTIFIED_MODERATOR(18),
       BOT_HTTP_INTERACTIONS(19),
       ACTIVE_DEVELOPER(22);

       private final int bitshift;

       Flags(int bitshift) {
           this.bitshift = bitshift;
       }

       public static int getFlagsAsInt(Flags... flags) {
           int flagVal = 0;
           for (Flags flag : flags) {
               flagVal += 1 << flag.bitshift;
           }
           return flagVal;
       }

       public static Flags[] getFlagsFromInt(int userFlags) {
           List<Flags> flagList = new LinkedList<>();
           for (Flags flag : Flags.values()) {
               if((userFlags >> flag.bitshift) % 2 == 1)
                   flagList.add(flag);
           }
           return flagList.toArray(new Flags[0]);
       }
   }

    public enum NitroType {
        NONE,
        NITRO_CLASSIC,
        NITRO,
        NITRO_BASIC;

        public static NitroType valueOfOrdinal(int ordinal) {
            return switch (ordinal) {
                case 0 -> NONE;
                case 1 -> NITRO_CLASSIC;
                case 2 -> NITRO;
                case 3 -> NITRO_BASIC;
                default -> null;
            };
        }
    }
}
