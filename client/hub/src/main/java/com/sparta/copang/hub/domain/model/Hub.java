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

    private String hub_name;
    private float latitude;
    private float longitude;
    private String hub_address;

    @OneToMany(mappedBy = "startHub", cascade = CascadeType.ALL)
    private List<Path> startPaths = new ArrayList<>();

    @OneToMany(mappedBy = "endHub", cascade = CascadeType.ALL)
    private List<Path> endPaths = new ArrayList<>();


    // FK (user_id)
    private UUID hub_manager;

    public static Hub createHub(HubReq req) {
        return Hub.builder()
                .hub_name(req.hub_name())
                .latitude(req.latitude())
                .longitude(req.longitude())
                .hub_address(req.hub_address())
                .hub_manager(req.manager_user_id())
                .build();
    }
}
