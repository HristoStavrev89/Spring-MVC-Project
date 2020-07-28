package com.holidayguru.data.repositories;

import com.holidayguru.data.entities.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {

    List<Host> findAllByUser_Id(String userId);

}
