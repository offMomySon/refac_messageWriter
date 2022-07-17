package common;

import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;

/**
 * 역할.
 * 메세지 출력 위치를 지정하기 위한 vo 객체.
 *
 */
@Getter
public class Destination {
    private final Address address;

    private final Usage usage;
    public Destination(@NonNull Address address, @NonNull Usage usage) {
        this.address = address;
        this.usage = usage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return Objects.equals(address, that.address) && usage == that.usage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, usage);
    }

    @Override
    public String toString() {
        return "common.Destination{" +
            "address=" + address +
            ", usage=" + usage +
            '}';
    }
}
