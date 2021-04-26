package com.perso.rest.api;

import model.OperationMessage;
import model.OperationResult;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.version-base-path}" + "/${spring.data.rest.base-path}")
public class CalculatorController {

    private final RabbitTemplate rabbitTemplate;
    private final Binding binding;

    public CalculatorController(RabbitTemplate rabbitTemplate, Binding binding) {
        this.rabbitTemplate = rabbitTemplate;
        this.binding = binding;
    }

    @GetMapping("health")
    public HttpStatus healthCheck() {
        return HttpStatus.OK;
    }

    @GetMapping("sum/{n1}/{n2}")
    public OperationResult sum(@PathVariable long n1, @PathVariable long n2) {
        return doIt(OperationMessage.OperationType.SUM, n1, n2);
    }

    @GetMapping("subtraction/{n1}/{n2}")
    public OperationResult subtraction(@PathVariable long n1, @PathVariable long n2) {
        return doIt(OperationMessage.OperationType.SUBTRACTION, n1, n2);
    }

    @GetMapping("multiplication/{n1}/{n2}")
    public OperationResult multiplication(@PathVariable long n1, @PathVariable long n2) {
        return doIt(OperationMessage.OperationType.MULTIPLICATION, n1, n2);
    }

    @GetMapping("division/{n1}/{n2}")
    public OperationResult division(@PathVariable long n1, @PathVariable long n2) {
        return doIt(OperationMessage.OperationType.DIVISION, n1, n2);
    }

    private OperationResult doIt(OperationMessage.OperationType operationType, long n1, long n2) {
        OperationMessage message = buildMessage(operationType, n1, n2);

        return rabbitTemplate.convertSendAndReceiveAsType(
                binding.getExchange(),
                binding.getRoutingKey(),
                message,
                ParameterizedTypeReference.<OperationResult>forType(OperationResult.class)
        );
    }

    private OperationMessage buildMessage(OperationMessage.OperationType operationType, double n1, double n2) {
        return OperationMessage.builder()
                .operationType(operationType)
                .n1(n1)
                .n2(n2)
                .build();
    }

}
