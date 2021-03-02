package markdown.parser;

import java.util.Objects;

public class MarkdownToken {
    private final MarkdownTokenType type;
    private final String value;

    public MarkdownToken(MarkdownTokenType type) {
        this.type = type;
        this.value = "";
    }

    public MarkdownToken(MarkdownTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public MarkdownToken(MarkdownTokenType type, char value) {
        this.type = type;
        this.value = String.valueOf(value);
    }

    public MarkdownTokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MarkdownToken{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkdownToken that = (MarkdownToken) o;
        return type == that.type && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
