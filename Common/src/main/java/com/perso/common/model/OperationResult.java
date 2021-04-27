package com.perso.common.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OperationResult extends AbstractMessage {
    private double result;
}
