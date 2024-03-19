package cn.spring.learning.mvc.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Jinhua-Lee
 */
@Getter
@AllArgsConstructor
public enum GenderType {

    /**
     * 男
     */
    MALE((byte) 1, "男"),
    /**
     * 女
     */
    FEMALE((byte) 2, "女");

    /**
     * 性别
     */
    private final byte type;
    /**
     * 性别描述
     */
    private final String text;

    public static Optional<GenderType> ofType(byte type) {
        return Arrays.stream(GenderType.values()).filter(genType ->
                Objects.equals(genType.type, type)
        ).findFirst();
    }

    public static Optional<GenderType> ofText(String text) {
        return Arrays.stream(GenderType.values()).filter(genType ->
                Objects.equals(genType.text, text)
        ).findFirst();
    }

    public static GenderType ofTypeThrows(byte type) {
        return ofType(type).orElseThrow(() ->
                new IllegalArgumentException(
                        String.format("未知的性别类型：type = %d", type)
                )
        );
    }

    public static GenderType ofTextThrows(String text) {
        return ofText(text).orElseThrow(() ->
                new IllegalArgumentException(
                        String.format("未知的性别描述：text = %s", text)
                )
        );
    }
}
