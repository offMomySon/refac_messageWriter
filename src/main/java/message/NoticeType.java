package message;

import lombok.Getter;

/**
 * 역할
 * noticeType 의 type 정의와 prefix 값 정의.
 */
@Getter
public enum NoticeType {
    INFO("\\u001B[33m[INFO]\\u001B[0m"), WARN("\\u001B[31m[WARN]\\u001B[0m");

    private final String prefix;

    NoticeType(String prefix) {
        this.prefix = prefix;
    }
}
