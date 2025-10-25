package dev.shaythesquog.components;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

@SuppressWarnings("unused")
public record Snowflake(long id) implements Comparable<Snowflake> {

    public Snowflake(String id) {
        this(Long.parseUnsignedLong(id));
    }

    public int compareTo(Snowflake other) {
        return Long.compareUnsigned(id, other.id);
    }

    @Override
    @NotNull
    public String toString() {
        return Long.toUnsignedString(id);
    }

    public Instant getTimestamp() {
        return Instant.ofEpochMilli((id >> 22) + 1420070400000L);
    }

    public byte getWorkerID() {
        return (byte) ((id &0x3E0000) >> 17);
    }

    public byte getProcessID() {
        return (byte) ((id & 0x1F000) >> 12);
    }

    public short getIncrement() {
        return (short) (id & 0xFFF);
    }
}
