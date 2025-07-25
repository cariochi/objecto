package com.cariochi.issuestest.factories;

import com.cariochi.objecto.Spec;
import java.time.Instant;

public interface DatesFactory {

    @Spec.Dates.Range(from = "1978-02-20T12:00:00Z", to = "1978-02-21T12:00:00Z")
    Instant instantGenerator();

}
