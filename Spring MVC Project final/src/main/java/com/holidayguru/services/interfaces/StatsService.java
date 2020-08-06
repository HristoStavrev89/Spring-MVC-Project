package com.holidayguru.services.interfaces;

import java.time.Instant;

public interface StatsService {

    void incRequestCount();

    int getRequestCount();

    Instant getStartedOn();

}
