package com.perso.common.model;

import lombok.*;

import java.util.UUID;

@Data
public abstract class AbstractMessage {
    private String uuid = UUID.randomUUID().toString();
}
