package com.sparta.copang.hub.domain.repository;

import com.sparta.copang.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository {

    Optional<Hub> findById(UUID id);

    Long countBy();

    @Query("SELECT h " +
    "FROM Hub h " +
    "WHERE h.hub_name LIKE %:query%")
    List<Hub> searchHub(@Param("query") String query);

    Optional<Hub> findBySequence(int sequence);

    Hub save(Hub hub);

    @Query(value = "WITH RECURSIVE HubPath AS (" +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, hr.distance as total_distance, hr.duration as total_duration " +
            "FROM p_hub_paths hr " +
            "WHERE " +
            "hr.start_hub_id = :startHubId " +
            "UNION ALL " +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, total_distance + hr.distance , total_duration + hr.duration " +
            "FROM p_hub_paths hr " +
            "INNER JOIN HubPath hp ON hr.start_hub_id = hp.end_hub_id" +
            ") " +
            "SELECT total_duration,total_distance " +
            "FROM HubPath " +
            "WHERE 1=1 " +
            "AND end_hub_id = :endHubId ",
            nativeQuery = true)
    List<Object[]> findPathForward(
            @Param("startHubId") UUID startHubId,
            @Param("endHubId") UUID endHubId);

    @Query(value = "WITH RECURSIVE HubPath AS (" +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, hr.distance as total_distance, hr.duration as total_duration " +
            "FROM p_hub_paths hr " +
            "WHERE " +
            "hr.end_hub_id = :startHubId " +
            "UNION ALL " +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, total_distance + hr.distance , total_duration + hr.duration " +
            "FROM p_hub_paths hr " +
            "INNER JOIN HubPath hp ON " +
            " hr.end_hub_id = hp.start_hub_id " +
            ") " +
            "SELECT total_duration,total_distance " +
            "FROM HubPath " +
            "WHERE 1=1 " +
            "AND start_hub_id = :endHubId ",
            nativeQuery = true)
    List<Object[]> findPathBackWard(
            @Param("startHubId") UUID startHubId,
            @Param("endHubId") UUID endHubId);

}
