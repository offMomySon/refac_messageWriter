package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Destinations {

    private final List<Predicate<Destination>> predicates;

    public Destinations(List<Predicate<Destination>> predicates) {
        this.predicates = predicates;
    }

    public void forEach(Consumer<Destination> d, Set<Destination> destinations) {
        l:for(Destination destination : destinations){
            for(Predicate<Destination> predicate: predicates){
                if(!predicate.test(destination)){
                    continue l;
                }
            }

            d.accept(destination);
        }
    }

    public void forEach2(Consumer<Destination> d, Set<Destination> destinations) {
        for(Destination destination : destinations){
            if(predicates.stream().anyMatch(p-> !p.test(destination))){
                continue;
            }

            d.accept(destination);
        }
    }

    public void forEach3(Consumer<Destination> d, Set<Destination> destinations) {
        Predicate<Destination>  mergedPredicate = predicates.stream().reduce(Predicate::and).orElse(dd-> true);

        destinations.stream().filter(mergedPredicate).forEach(d);
    }

//    public Destinations combine(Destinations destinations){
//        Set<Destination> newDestination = new HashSet<>(value);
//        newDestination.addAll(destinations.value);
//
//        return new Destinations(newDestination);
//    }

    public static class Builder {
        private List<Predicate<Destination>> predicates = new ArrayList<>();
        private Predicate<Destination> predicate;

        public Builder filtered(Predicate<Destination> predicate){
            predicates.add(predicate);
            return this;
        }

        public Builder filtered2(Predicate<Destination> predicate){
            if(Objects.isNull(this.predicate)){
                this.predicate = predicate;
                return this;
            }

            this.predicate = this.predicate.and(predicate);
            return this;
        }

        public Destinations build(){
            return new Destinations(predicates);
        }

    }
}
