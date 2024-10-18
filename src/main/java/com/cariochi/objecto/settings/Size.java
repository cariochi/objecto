package com.cariochi.objecto.settings;

import com.cariochi.objecto.ObjectoRandom;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

@Value
@With
@Accessors(fluent = true)
@Builder(access = AccessLevel.PACKAGE)
public class Size {

    Integer value;
    Range<Integer> range;

    public int generate(ObjectoRandom random) {
        return Optional.ofNullable(value)
                .orElseGet(() -> random.nextInt(range.from(), range.to()));
    }
}
