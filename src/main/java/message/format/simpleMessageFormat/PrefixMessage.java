package message.format.simpleMessageFormat;

import java.text.MessageFormat;
import lombok.NonNull;
import message.NoticeType;
import org.apache.commons.lang3.StringUtils;

/**
 * 역할.
 * prefix 메세지(view) 를 생성하는 역할.
 */
public class PrefixMessage {
    private final String message;

    private PrefixMessage(@NonNull String message) {
        if(StringUtils.isEmpty(message)) {
            throw new RuntimeException(MessageFormat.format("message is empty. = `{}`", message));
        }
        if(StringUtils.isBlank(message)) {
            throw new RuntimeException(MessageFormat.format("message is blank. = `{}`", message));
        }
        this.message = message;
    }

    public static PrefixMessage of(@NonNull NoticeType noticeType){
        return new PrefixMessage(noticeType.getPrefix());
    }

    public String create() {
        return message;
    }
}
