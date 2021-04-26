package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OperationMessage {

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
