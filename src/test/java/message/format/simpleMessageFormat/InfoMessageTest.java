package message.format.simpleMessageFormat;

import java.text.MessageFormat;
import message.NoticeType;
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
class InfoMessageTest {

    @DisplayName("blank, null, empty 이면 메세지가 생성 되지 않습니다.")
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = " ")
    void test1(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(() -> new InfoMessage(sMessage));

        //then
        Assertions.assertThat(actual).isNotNull();
    }

    @DisplayName("올바른 string 이면 객체가 생성됩니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test2(String sMessage){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(() -> new InfoMessage(sMessage));

        //then
        Assertions.assertThat(actual).isNull();
    }

    @DisplayName("Info 포멧의 메세지를 생성합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"test message","not empty message", "  not blank message"})
    void test3(String sMessage){
        //given
        InfoMessage infoMessage = new InfoMessage(sMessage);

        //when
        String actual = infoMessage.create();

        //then
        String format = MessageFormat.format("1:{0}", NoticeType.INFO.getPrefix());
        Assertions.assertThat(actual).contains(format);
    }

}