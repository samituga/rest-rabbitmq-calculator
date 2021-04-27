package com.perso.calculator;

import lombok.extern.slf4j.Slf4j;
import com.perso.common.model.OperationMessage;
import com.perso.common.model.OperationResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component
@Slf4j
public class CalculatorConsumer {

    private final Map<OperationMessage.OperationType, BiFunction<Double, Double, Double>> operationHandlers;

    public CalculatorConsumer() {
        operationHandlers = new HashMap<>();
        this.registerHandlers();
    }

    public OperationResult messageFromQueue(OperationMessage message) {
        log.info("Received message, UUID: " + message.getUuid() + " payload: " + message);
        double operationResult = operationHandlers.get(message.getOperationType()).apply(message.getN1(), message.getN2());
        OperationResult result = OperationResult.builder()
                .result(operationResult)
                .build();
        result.setUuid(message.getUuid());
        return result;
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
