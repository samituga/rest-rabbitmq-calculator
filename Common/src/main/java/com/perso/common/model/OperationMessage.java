package com.perso.common.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OperationMessage extends AbstractMessage {

    public enum OperationType {
        SUM,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION
    }

    private OperationType operationType;

    private double n1;
    private double n2;
}
