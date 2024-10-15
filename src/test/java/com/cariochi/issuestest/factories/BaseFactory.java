package com.cariochi.issuestest.factories;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.TypeFactory;
import java.time.Instant;

public interface BaseFactory {

    @TypeFactory
    @Settings.Dates.Range(min = "1978-02-20T12:00:00Z", max = "1978-02-20T12:00:00Z")
    Instant instantGenerator();

}
