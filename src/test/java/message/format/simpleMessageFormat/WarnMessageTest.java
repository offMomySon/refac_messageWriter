package message.format.simpleMessageFormat;

import java.text.MessageFormat;
import message.NoticeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 역할일치?
 * 기존에 설정한 WarnMessage 가 warn 타입의 message 를 생성하는 역할을 가진다.
 * 테스트 코드에서도 동일하게 create method 의 테스트 내용이 warn 형태의 메세지 일치여부를 확인하기 때문에
 * 테스트코드에서의 객체가 수행하는 역할과 정의한 역할이 일치한다.
 *
 * test suit 복잡도, 이상한점?
 * string 값을 통해 warn message 객체를 생성하기 때문에 이상하지 않다.
 *
 * 테스트 코드의 이상한점?
 * 생성된 메세지의 warn 포멧 여부를 검사하고 있기 때문에 이상하지 않다.
 */
class WarnMessageTest {

    @DisplayName("blank, null, empty 이면 exception 이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void test(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(() -> new WarnMessage(sMessage));

        //then
        Assertions.assertThat(actual).isNotNull();
    }

    @DisplayName("올바른 string 값이면 메세지 객체가 생성됩니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test1(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()->new WarnMessage(sMessage));

        //then
        Assertions.assertThat(actual).isNull();
    }

    @DisplayName("Warn 포멧의 메세지를 생성합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test2(String sMessage){
        //given
        WarnMessage message = new WarnMessage(sMessage);

        //when
        String actual = message.create();

        //then
        String format = MessageFormat.format("1:{0}", NoticeType.WARN.getPrefix());
        Assertions.assertThat(actual).contains(format);
    }

 }