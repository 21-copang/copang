package com.sparta.copang.hub.domain.model;

import com.sparta.copang.hub.presentation.dtos.PathReq;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_hub_paths")
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID path_id;

    private LocalTime duration;
    private float distance;

    private UUID start_hub_id;
    private UUID end_hub_id;

    public static Path createPath(PathReq req) {
        return Path.builder()
                .duration(req.duration())
                .distance(req.distance())
                .start_hub_id(req.start_hub_id())
                .end_hub_id(req.end_hub_id())
                .build();
    }
}