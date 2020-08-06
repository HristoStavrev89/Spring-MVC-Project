package com.holidayguru.services.implementations;

import com.holidayguru.services.interfaces.StatsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatsServiceImpl implements StatsService {

    private AtomicInteger requestCount = new AtomicInteger(0);
    private Instant startedOn = Instant.now();




    @Override
    public void incRequestCount() {
        this.requestCount.incrementAndGet();
    }

    @Override
    public int getRequestCount() {
        return this.requestCount.get();
    }

    @Override
    public Instant getStartedOn() {
        return this.startedOn;
    }
}
