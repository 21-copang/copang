package com.sparta.copang.hub.domain.repository;

import com.sparta.copang.hub.domain.model.Hub;

import java.util.Optional;
import java.util.UUID;

public interface HubRepository {

    Optional<Hub> findById(UUID id);
    Hub save(Hub hub);
}
