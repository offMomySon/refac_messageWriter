import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public enum Usage {
    FILE, SOCKET;

    public static Optional<Usage> find(String sUsage){
        return Stream.of(Usage.values())
            .filter(u -> StringUtils.equalsIgnoreCase(u.name(), sUsage))
            .findAny();
    }
}
