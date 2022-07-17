package common;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 역할.
 * message 의 목적지에 대한 값( ip string 을[원시값]) 객체로 다루기 위해 사용한다.
 *
 * vo 란.
 * 원시 값은 해석하는 방법에 따라 다양하게 해석되어질 수 있다.
 * 192.168.0.1 이 ip address 가 아니라 passwd 로 사용될 수 있다.
 * 다르게 해석 됨에따라 전혀 다른 의도의 로직으로 사용될 가능성이 있다.
 * 이를 방지하기 위해, 원시값을 객체로 감싸고 하나의 의미로 사용하기 위해 vo 객체를 구성한다.
 *
 * vo 간의 연산을 위해 equal, hash code 구현.
 */
public class Address {
    private static final Range<Integer> DIGIT_RANGE = Range.between(0, 255);
    private static final int PART_SIZE = 4;
    private static final String DELIMITER = "\\.";
    private final String value;
    public Address(@NonNull String value) {
        if(StringUtils.isEmpty(value)){
            throw new RuntimeException("value 가 empty 입니다.");
        }
        if(StringUtils.isBlank(value)){
            throw new RuntimeException("value 가 blanck 입니다.");
        }

        this.value = validate(value);
    }

    private static String validate(String value) {
        String[] splitAddress = value.split(DELIMITER);

        if(notAddressPartSize(splitAddress.length)){
            throw new RuntimeException("not ipV4 address part size.");
        }
        if(notNumbericAddresses(splitAddress)){
            throw new RuntimeException("not numberic address.");
        }
        if(notSuitableRange(Stream.of(splitAddress).map(Integer::parseInt).collect(Collectors.toUnmodifiableList()))){
            throw new RuntimeException("not suitable range.");
        }
        return value;
    }

    private static boolean notSuitableRange(List<Integer> splitedAddress) {
        return splitedAddress.stream().anyMatch(it -> !DIGIT_RANGE.contains(it));
    }
    private static boolean notNumbericAddresses(String[] splitedAddress) {
        return Stream.of(splitedAddress).anyMatch( it -> !NumberUtils.isParsable(it));
    }
    private static boolean notAddressPartSize(int splitAddressSize) {
        return PART_SIZE != splitAddressSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "common.Address{" +
            "value='" + value + '\'' +
            '}';
    }
}
