package com.sparta.copang.order.application.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull(message = "OrderCompanyId is null")
        UUID orderCompanyId,

        @NotNull(message = "ReceiveCompanyId is null")
        UUID receiveCompanyId,

        @NotNull(message = "HubId is null")
        UUID hubId,

        @NotBlank(message = "Receiver cannot be blank")
        String receiver,

        @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}", message = "Invalid Email Input")
        String slackEmail,

        @NotBlank(message = "Address cannot be blank")
        String address,

        @Valid
        @Size(min = 1, message = "ProductList is empty")
        List<ProductRequestData> productList
) {

}
