package message.format.simpleMessageFormat;

import message.NoticePrefix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 역할일치?
 * 테스트하고 있는 create mehod 에서 생성되는 문장이, generic 형태의 message 일치여부를 확인한다.
 * 기존 설정하였던 GenericMessage 의 역할과 일치한다.
 *
 * test suit 복잡도?
 * 메세지 생성에 필요한 string 만을 전달하여 복작한 test suit 를 가지지 않는다.
 *
 * 테스트 코드의 이상한점?
 * 생성된 메세지의 포멧을 검사하고 있기 때문에 이상하지 않다.
 */
class GenericMessageTest extends SimpleMessageFormatMessageTest{

    @Override
    protected SimpleMessageFormatMessage createMessage(String sMessage) {
        return new GenericMessage(sMessage);
    }

    @Override
    protected String getCode() {
        return "0";
    }
}