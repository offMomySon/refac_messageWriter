package message.format.simpleMessageFormat;

import java.text.MessageFormat;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * 역할
 * prefixMessage 와 message 로 notification 메세지(view) 를 만드는 역할.
 */
public abstract class NotificationMessage implements SimpleMessageFormatMessage{
   private final String message;

    protected NotificationMessage(@NonNull String message) {
        if(StringUtils.isEmpty(message)) {
            throw new RuntimeException(MessageFormat.format("message is empty. = `{}`", message));
        }
        if(StringUtils.isBlank(message)) {
            throw new RuntimeException(MessageFormat.format("message is blank. = `{}`", message));
        }
        this.message = message;
    }

    @Override
    public String create() {
        return MessageFormat.format("1:{0}", message);
    }
}
