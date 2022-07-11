package message;

import lombok.Getter;

@Getter
public enum Subject {
    SERVER("[서버]"), CLIENT("[클라]"), INFO("[INFO]"), WARN("[WARN]");

    private final String value;

    Subject(String value) {
        this.value = value;
    }
}
