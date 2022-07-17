package message.format.simpleMessageFormat;

import java.text.MessageFormat;
import message.NoticePrefix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 역할일치?
 * 테스트하고 있는 create method 에서 생성되는 문장이, notice info 형태의 message 일치여부를 확인한다.
 * 기존 설정하였던 InfoMessage 클래스의 역할인 'info 포멧 메세지를 생성한다' 와 일치한다.
 *
 * test suit 복잡도?
 * 메세지 생성에 필요한 string 만을 전달하여 복작한 test suit 를 가지지 않는다.
 *
 * 테스트 코드의 이상한점?
 * 생성된 메세지의 info 포멧 여부를 검사하고 있기 때문에 이상하지 않다.
 */
class InfoNotificationMessageTest extends NotificationMessageTest{

    @Override
    protected NoticePrefix getNoticePrefix() {
        return NoticePrefix.INFO;
    }

    @Override
    protected SimpleMessageFormatMessage createMessage(String sMessage) {
        return new InfoNotificationMessage(sMessage);
    }
}