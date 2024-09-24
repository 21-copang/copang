package com.sparta.copang.hub.domain.model;

import lombok.Getter;

@Getter
public enum WorkerRoleEnum {
    HUB_DELIVERY(Authority.HUB_DELIVERY),
    COMPANY_DELIVERY(Authority.COMPANY_DELIVERY);

    private final String authority;

    WorkerRoleEnum(String authority) {
        this.authority = authority;
    }

    private static class Authority {
        public static final String HUB_DELIVERY = "hub_delivery";
        public static final String COMPANY_DELIVERY = "company_delivery";
    }
}
