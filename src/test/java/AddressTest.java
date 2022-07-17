import common.Address;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 역할일치?
 * string 포멧의 ip address 를 vo 로 다루기 위해 address 객체를 생성하였다.
 * address 객체를 값으로써 다루기 위해 equal, hashcode 을 구현하였다.
 *
 * test suit 복잡도?
 * vo 를 생성을 위한 string 값 하나가 필요하다. 그렇기 때문에 복잡도는 높지않다.
 * 하지만, vo 객체의 value 의 조건이 까다롭기 때문에 다양한 테스트가 필요하다.
 *
 * 테스트 코드의 이상한점?
 * 없는것 같습니다.
 */
class AddressTest {

    @DisplayName("ipv4 포멧을 가진 값을 받으면 객체가 생성됩니디.")
    @ParameterizedTest
    @ValueSource(strings = {"001.001.001.001","192.168.0.1", "225.1.1.1", "123.123.123.123", "233.0.1.0", "255.255.255.255"})
    void test2(String address){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new Address(address));

        //then
        Assertions.assertThat(actual)
            .isNull();
    }

    @DisplayName("address 값이 empty, blank, null 이면 exception 이 발생합니다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void test1(String address){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(() -> new Address(address));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("address 값이 ipv4 포멧의 사이즈로 분할되지 않으면 exception 이 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "1.2", "1.2.3", "1.2.3.4.5", "1.2.3.4.5.6", "1.2.3.4.5.6.7"})
    void test3(String address) {
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new Address(address));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("address 값이 숫자가 아니면 exception 이 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"aaa.aaa.aaa.aaa", "aaa.aaa.bbb.ccc", "aaa.bbb.ccc.ddd", "123.123.123.abc", "123.123.123.!!!"})
    void test4(String address){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new Address(address));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("address 값이 ipv4 프로토콜의 범위에 존재하지 않으면 exception 이 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"001.001.001.256", "001.001.256.256", "001.256.256.256", "256.256.256.256", "1000.1000.1000.1000"})
    void test5(String address) {
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new Address(address));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("값이 동일한 객체이면 eqaul 비교시 true 를 반환합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"001.001.001.001", "002.002.002.002", "123.123.123.123", "225.225.225.225", "1.2.3.4"})
    void test6(String address){
        //given
        Address address1 = new Address(address);
        Address address2 = new Address(address);

        //when
        boolean actual = Objects.equals(address1, address2);

        //then
        Assertions.assertThat(actual)
            .isTrue();
    }

    @DisplayName("값이 동일한 객체가 아니면 equal 비교시 false 를 반환합니다.")
    @ParameterizedTest
    @CsvSource(value= {
        "001.001.001.001, 001.001.001.002",
        "002.002.002.002, 003.003.003.003",
        "123.123.123.123, 123.123.123.124",
        "225.225.225.224, 225.225.225.225",
    })
    void test7(String sAddress1, String sAddress2){
        //given
        Address address1 = new Address(sAddress1);
        Address address2 = new Address(sAddress2);

        //when
        boolean actual = Objects.equals(address1, address2);

        //then
        Assertions.assertThat(actual)
            .isFalse();
    }

    @DisplayName("값이 동일한 객체이면 같은 hash code 를 생성합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"001.001.001.001", "002.002.002.002", "123.123.123.123", "225.225.225.225", "1.2.3.4"})
    void test8(String sAddress){
        //given
        int hashCode1 = new Address(sAddress).hashCode();
        int hashCode2 = new Address(sAddress).hashCode();

        System.out.println(hashCode1);
        System.out.println(hashCode2);

        //when
        boolean actual = Objects.equals(hashCode1, hashCode2);

        //then
        Assertions.assertThat(actual)
            .isTrue();
    }

    @DisplayName("값이 동일한 객체가 아니면 다른 hash code 를 생성합니다.")
    @ParameterizedTest
    @CsvSource(value= {
        "001.001.001.001, 001.001.001.002",
        "002.002.002.002, 003.003.003.003",
        "123.123.123.123, 123.123.123.124",
        "225.225.225.224, 225.225.225.225"
    })
    void test9(String sAddress1, String sAddress2){
        //given
        int hashCode1 = new Address(sAddress1).hashCode();
        int hashCode2 = new Address(sAddress2).hashCode();

        //when
        boolean actual = Objects.equals(hashCode1, hashCode2);

        //then
        Assertions.assertThat(actual)
            .isFalse();
    }
}