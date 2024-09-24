package com.sparta.copang.hub.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
@Table(name = "p_workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID workerId;

    @Enumerated(EnumType.STRING)
    private WorkerRoleEnum role;

    private String slackId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;

    private UUID userId;

}
