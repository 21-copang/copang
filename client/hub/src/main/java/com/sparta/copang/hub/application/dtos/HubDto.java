package com.sparta.copang.hub.application.dtos;

import com.sparta.copang.hub.domain.model.Hub;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
public final class HubDto implements Serializable {
    private final UUID hub_id;
    private final String hub_name;
    private final float latitude;
    private final float longitude;
    private final String hub_address;
    private final UUID hub_manager;

    public HubDto(
            UUID hub_id,
            String hub_name,
            float latitude,
            float longitude,
            String hub_address,
            UUID hub_manager) {
        this.hub_id = hub_id;
        this.hub_name = hub_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hub_address = hub_address;
        this.hub_manager = hub_manager;
    }

    public static HubDto of(Hub hub) {
        return HubDto.builder()
                .hub_id(hub.getHub_id())
                .hub_name(hub.getHub_name())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .hub_address(hub.getHub_address())
                .hub_manager(hub.getHub_manager())
                .build();
    }

    public UUID hub_id() {
        return hub_id;
    }

    public String hub_name() {
        return hub_name;
    }

    public float latitude() {
        return latitude;
    }

    public float longitude() {
        return longitude;
    }

    public String hub_address() {
        return hub_address;
    }

    public UUID hub_manager() {
        return hub_manager;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (HubDto) obj;
        return Objects.equals(this.hub_id, that.hub_id) &&
                Objects.equals(this.hub_name, that.hub_name) &&
                Float.floatToIntBits(this.latitude) == Float.floatToIntBits(that.latitude) &&
                Float.floatToIntBits(this.longitude) == Float.floatToIntBits(that.longitude) &&
                Objects.equals(this.hub_address, that.hub_address) &&
                Objects.equals(this.hub_manager, that.hub_manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hub_id, hub_name, latitude, longitude, hub_address, hub_manager);
    }

    @Override
    public String toString() {
        return "HubDto[" +
                "hub_id=" + hub_id + ", " +
                "hub_name=" + hub_name + ", " +
                "latitude=" + latitude + ", " +
                "longitude=" + longitude + ", " +
                "hub_address=" + hub_address + ", " +
                "hub_manager=" + hub_manager + ']';
    }

}
