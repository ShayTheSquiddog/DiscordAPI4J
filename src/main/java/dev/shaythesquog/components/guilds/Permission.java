package dev.shaythesquog.components.guilds;

import dev.shaythesquog.components.Util;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public enum Permission {
    CREATE_INSTANT_INVITE,
    KICK_MEMBERS,
    BAN_MEMBERS,
    ADMINISTRATOR,
    MANAGE_CHANNELS,
    MANAGE_GUILD,
    ADD_REACTIONS,
    VIEW_AUDIT_LOG,
    PRIORITY_SPEAKER,
    STREAM,
    VIEW_CHANNEL,
    SEND_MESSAGES,
    SEND_TTS_MESSAGES,
    MANAGE_MESSAGES,
    EMBED_LINKS,
    ATTACH_FILES,
    READ_MESSAGE_HISTORY,
    MENTION_EVERYONE,
    USE_EXTERNAL_EMOJIS,
    VIEW_GUILD_INSIGHTS,
    CONNECT,
    SPEAK,
    MUTE_MEMBERS,
    DEAFEN_MEMBERS,
    MOVE_MEMBERS,
    USE_VAD,
    CHANGE_NICKNAME,
    MANAGE_NICKNAMES,
    MANAGE_ROLES,
    MANAGE_WEBHOOKS,
    MANAGE_GUILD_EXPRESSIONS,
    USE_APPLICATION_COMMANDS,
    REQUEST_TO_SPEAK,
    MANAGE_EVENTS,
    MANAGE_THREADS,
    CREATE_PUBLIC_THREADS,
    CREATE_PRIVATE_THREADS,
    USE_EXTERNAL_STICKERS,
    SEND_MESSAGES_IN_THREADS,
    USE_EMBEDDED_ACTIVITIES,
    MODERATE_MEMBERS,
    VIEW_CREATOR_MONETIZATION_ANALYTICS,
    USE_SOUNDBOARD,
    CREATE_GUILD_EXPRESSIONS,
    CREATE_EVENTS,
    USE_EXTERNAL_SOUNDS,
    SEND_VOICE_MESSAGES,
    SEND_POLLS,
    USE_EXTERNAL_APPS,
    PIN_MESSAGES;

    public static Permission[] getPermissionsFromBitSet(BitSet bitSet) {
        List<Permission> perms = new LinkedList<>();
        for (Permission p : values()) {
            if(bitSet.get(p.ordinal())) {
                perms.add(p);
            }
        }
        return perms.toArray(new Permission[0]);
    }

    public static BitSet getPermissionsAsBitSet(Permission... perms) {
        BitSet permsSet = new BitSet(Permission.values().length);
        for(Permission p : perms) {
            permsSet.set(p.ordinal());
        }
        return permsSet;
    }

    public static BigInteger getPermissionsAsBigInteger(Permission... perms) {
        return new BigInteger(1, Util.byteEndianConversion(getPermissionsAsBitSet(perms).toByteArray()));
    }

    public static Permission[] getPermissionsFromBigInteger(BigInteger bigInt) {
        return getPermissionsFromBitSet(BitSet.valueOf(Util.byteEndianConversion(bigInt.toByteArray())));
    }
}
