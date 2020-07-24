package com.holidayguru.data.repositories;

import com.holidayguru.data.entities.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {
}
