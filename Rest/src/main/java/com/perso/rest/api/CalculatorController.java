package com.perso.rest.api;

import lombok.extern.slf4j.Slf4j;
import com.perso.common.model.OperationMessage;
import com.perso.common.model.OperationResult;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.version-base-path}" + "/${spring.data.rest.base-path}")
@Slf4j
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

    @GetMapping("sum")
    public OperationResult sum(@RequestParam double n1, @RequestParam double n2) {
        return doIt(OperationMessage.OperationType.SUM, n1, n2);
    }

    @GetMapping("subtraction")
    public OperationResult subtraction(@RequestParam double n1, @RequestParam double n2) {
        return doIt(OperationMessage.OperationType.SUBTRACTION, n1, n2);
    }

    @GetMapping("multiplication")
    public OperationResult multiplication(@RequestParam double n1, @RequestParam double n2) {
        return doIt(OperationMessage.OperationType.MULTIPLICATION, n1, n2);
    }

    @GetMapping("division")
    public OperationResult division(@RequestParam double n1, @RequestParam double n2) {
        return doIt(OperationMessage.OperationType.DIVISION, n1, n2);
    }

    private OperationResult doIt(OperationMessage.OperationType operationType, double n1, double n2) {
        OperationMessage message = buildMessage(operationType, n1, n2);

        OperationResult result = rabbitTemplate.convertSendAndReceiveAsType(
                binding.getExchange(),
                binding.getRoutingKey(),
                message,
                ParameterizedTypeReference.<OperationResult>forType(OperationResult.class)
        );

        if (result != null) {
            log.info("Received response, UUID: " + result.getUuid() + " payload: " + result);
        } else {
            log.warn("No response received for UUID: " + message.getUuid());
        }
        return result;
    }

    private OperationMessage buildMessage(OperationMessage.OperationType operationType, double n1, double n2) {
        return OperationMessage.builder()
                .operationType(operationType)
                .n1(n1)
                .n2(n2)
                .build();
    }

}
