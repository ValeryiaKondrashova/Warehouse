package mwarehouse.warehouse.entity;

import java.io.Serializable;

public enum Command implements Serializable {
    CREATE,
    READ,
    UPDATE,
    DELETE,
    EXIT,
    READ1,
    DELETE1,
}
