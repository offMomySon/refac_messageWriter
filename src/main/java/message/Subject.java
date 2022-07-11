package message;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Subject {
    SERVER("[서버]"), CLIENT("[클라]"), INFO("[INFO]"), WARN("[WARN]");

    private final String value;

    Subject(String value) {
        this.value = value;
    }

    public static Optional<Subject> find(String name){
        return Stream.of(values())
            .filter(s -> StringUtils.equals(s.name(), name))
            .findAny();
    }
}
