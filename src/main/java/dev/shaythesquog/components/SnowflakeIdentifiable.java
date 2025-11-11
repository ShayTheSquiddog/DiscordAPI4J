package dev.shaythesquog.components;

public abstract class SnowflakeIdentifiable {
    public final Snowflake id;

    protected SnowflakeIdentifiable(Snowflake id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof SnowflakeIdentifiable) {
            return id.equals(((SnowflakeIdentifiable)o).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
