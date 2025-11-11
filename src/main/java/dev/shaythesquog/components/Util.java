package dev.shaythesquog.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Optional;

@SuppressWarnings({"unused", "RedundantLabeledSwitchRuleCodeBlock"})
public class Util {
    private Util(){}

    public static BigInteger bigIntFromBitSet(BitSet bitSet) {
        return new BigInteger(1, byteEndianConversion(bitSet.toByteArray()));
    }

    /**
     *
     * @param bytes An array of bytes to reverse the endian of
     * @return A copy of {@code bytes} in reverse order, effectively flipping the endian representation
     */
    public static byte[] byteEndianConversion(byte... bytes) {
        byte[] output = new byte[bytes.length];
        for(int i = bytes.length; i > 0; i--) {
            output[i - 1] = bytes[bytes.length - i];
        }
        return output;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> void addNullableOptionalIfPresent(@Nullable Optional<T> container, String containerName, JsonObject addable) {
        if(container == null) {
            addable.add(containerName, null);
        } else {
            container.ifPresent(obj -> {
                switch (obj) {
                    case JsonAPIComponent jsonAPIObj -> addable.add(containerName, jsonAPIObj.toJson());
                    case Snowflake sf -> addable.addProperty(containerName, sf.toString());
                    case Instant instant -> addable.addProperty(containerName, instant.toString());
                    case Boolean bool -> addable.addProperty(containerName, bool);
                    case String string -> addable.addProperty(containerName, string);
                    case Number num -> addable.addProperty(containerName, num);
                    case Character character -> addable.addProperty(containerName, character);
                    default -> {
                        addable.addProperty(containerName, obj.toString());
                        // TODO some logger code here
                    }
                }
            });
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <TArr> void addNullableOptionalArrayIfPresent(@Nullable Optional<TArr[]> container, String containerName, JsonObject addable) {
        if(container == null) {
            addable.add(containerName, null);
        } else {
            container.ifPresent(obj -> {
               JsonArray arr = new JsonArray(obj.length);
               Arrays.stream(obj).forEachOrdered(obj1 -> {
                   switch (obj1) {
                       case JsonAPIComponent jsonAPIObj -> arr.add(jsonAPIObj.toJson());
                       case Snowflake snowflake -> arr.add(snowflake.toString());
                       case Instant instant -> arr.add(instant.toString());
                       case Boolean bool -> arr.add(bool);
                       case String string -> arr.add(string);
                       case Number num -> arr.add(num);
                       case Character character -> arr.add(character);
                       case null -> arr.add(JsonNull.INSTANCE);
                       default -> {
                           arr.add(obj1.toString());
                           // TODO some logger code here
                       }
                   }
               });

            });
        }
    }
}
