package com.example.orders.enums;

/**
 * enum для статусов заказов
 */
public enum Status {
    IN_ORDER("в ожидании"),
    PROCESSING("в процессе"),
    COMPLETED("завершен"),
    DENIED("отменен");

    private final String status;
    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
