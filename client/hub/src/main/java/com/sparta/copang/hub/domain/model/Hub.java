package com.sparta.copang.hub.domain.model;

import com.sparta.copang.hub.common.Entity.Audit;
import com.sparta.copang.hub.presentation.dtos.HubReq;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Where(clause = "deleted_at is null")
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_hubs")
public class Hub extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hub_id;

    @Column(nullable = false)
    private String hub_name;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;

    @Column(nullable = false)
    private String hub_address;

    @Column(nullable = false)
    private int sequence;

    @OneToMany(mappedBy = "startHub", cascade = CascadeType.ALL)
    private List<Path> startPaths = new ArrayList<>();

    @OneToMany(mappedBy = "endHub", cascade = CascadeType.ALL)
    private List<Path> endPaths = new ArrayList<>();


    // FK (user_id)
    private UUID hub_manager;

    public Hub(String hub_name, float latitude, float longitude, String hub_address, int sequence) {
        this.hub_name = hub_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hub_address = hub_address;
        this.sequence = sequence;
    }

    public static Hub createHub(HubReq req) {
        return Hub.builder()
                .hub_name(req.hub_name())
                .latitude(req.latitude())
                .longitude(req.longitude())
                .hub_address(req.hub_address())
                .sequence(req.sequence())
                .hub_manager(req.manager_user_id())
                .build();
    }
}
