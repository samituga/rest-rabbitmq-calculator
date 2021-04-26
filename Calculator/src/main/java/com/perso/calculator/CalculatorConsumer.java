package com.perso.calculator;

import model.OperationMessage;
import model.OperationResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component
public class CalculatorConsumer {

    private final Map<OperationMessage.OperationType, BiFunction<Double, Double, Double>> operationHandlers;

    public CalculatorConsumer() {
        operationHandlers = new HashMap<>();
        this.registerHandlers();
    }

    public OperationResult messageFromQueue(OperationMessage message) {
        double result = operationHandlers.get(message.getOperationType()).apply(message.getN1(), message.getN2());
        return OperationResult.builder()
                .result(result)
                .build();
    }

    private void registerHandlers() {
        operationHandlers.put(OperationMessage.OperationType.SUM, this::sum);
        operationHandlers.put(OperationMessage.OperationType.SUBTRACTION, this::subtraction);
        operationHandlers.put(OperationMessage.OperationType.MULTIPLICATION, this::multiplication);
        operationHandlers.put(OperationMessage.OperationType.DIVISION, this::division);
    }

    private double sum(double n1, double n2) {
        return n1 + n2;
    }

    private double subtraction(double n1, double n2) {
        return n1 - n2;
    }

    private double multiplication(double n1, double n2) {
        return n1 * n2;
    }

    private double division(double n1, double n2) {
        return n1 / n2;
    }
}
