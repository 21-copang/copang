package com.sparta.copang.hub.domain.repository;

import com.sparta.copang.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository {

    Optional<Hub> findById(UUID id);

    @Query("SELECT h " +
    "FROM Hub h " +
    "WHERE h.hub_name LIKE %:query%")
    List<Hub> searchHub(@Param("query") String query);

    Hub save(Hub hub);
}
