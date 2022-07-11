package message.format.file;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import message.Subject;
import org.apache.commons.lang3.StringUtils;

/**
 * 역할.
 * 특정시간 file 메세지( view ) 를 생성하는 역할.
 */
public class FileMessage {
    private static final DateTimeFormatter MESSAGE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private final Subject subject;
    private final String message;

    public FileMessage(@NonNull Subject subject, @NonNull String message) {
        if(StringUtils.isEmpty(message)) {
            throw new RuntimeException(MessageFormat.format("message is empty. = `{}`", message));
        }
        if(StringUtils.isBlank(message)) {
            throw new RuntimeException(MessageFormat.format("message is blank. = `{}`", message));
        }
        this.subject = subject;
        this.message = message;
    }

    public String create(LocalDateTime dateTime){
        return MessageFormat.format("{0} {1} {2}", MESSAGE_TIME_FORMAT.format(dateTime), subject.getValue(), message);
    }
}
