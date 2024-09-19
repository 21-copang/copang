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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_hub_id", nullable = false)
    private Hub startHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_hub_id", nullable = false)
    private Hub endHub;

    public static Path createPath(PathReq req, Hub start_hub, Hub end_hub) {
        return Path.builder()
                .duration(req.duration())
                .distance(req.distance())
                .startHub(start_hub)
                .endHub(end_hub)
                .build();
    }
}