package message.format.simpleMessageFormat;

import message.NoticePrefix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * 역할일치?
 * message 를 받아 prefix 메세지를 생성하고있다.
 * 기존에 설정한 prefix 메세지를 생성한다와 같은 역할을 하고 있다.
 *
 * test suit 의 이상한점, 복잡도?
 * prefix 객체를 만드는 방법은 noticeType 으로만 생성가능하기 때문에
 * test suit 에서 이상한점이 존재하지 않는다.
 * 복잡도 또한 noticeType 으로만 생성가능하기 때문에 복잡하지 않다.
 *
 * 테스트 코드의 이상한점.
 * 생성된 prefix 의 message 가 type 의 prefix 값 자체와 동일하기 때문에
 * 일치여부로 테스트를 진행한다.
 */
class PrefixMessageTest {

    @DisplayName("NoticeType 으로 객체를 생성합니다.")
    @ParameterizedTest
    @EnumSource(value = NoticePrefix.class)
    void test1(NoticePrefix type){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(() -> PrefixMessage.of(type));

        //then
        Assertions.assertThat(actual).isNull();
    }

    @DisplayName("prefix 메세지를 생성합니다.")
    @ParameterizedTest
    @EnumSource(value = NoticePrefix.class)
    void test2(NoticePrefix type){
        //given
        PrefixMessage prefixMessage = PrefixMessage.of(type);

        //when
        String actual = prefixMessage.create();

        //then
        Assertions.assertThat(actual).isEqualTo(type.getValue());
    }

}