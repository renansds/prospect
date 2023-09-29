package com.siqueira.prospect.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siqueira.prospect.dto.input.ClienteFila;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FilaAtendimentoSQSService {
    private final SqsTemplate sqsTemplate;

    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;


    @Value("${spring.cloud.aws.sqs.endpoint}")
    private String QUEUE = "atendimento-fila-sqs";

    public FilaAtendimentoSQSService(SqsTemplate sqsTemplate, AmazonSQS amazonSQS, ObjectMapper objectMapper) {
        this.amazonSQS = amazonSQS;
        this.sqsTemplate = sqsTemplate;
        this.objectMapper = objectMapper;
    }

    public void adicionarClienteNaFila(ClienteFila cliente) {
        sendMessageToSQS(cliente);
    }

    public void atualizarClienteNaFila(ClienteFila cliente) {
        deleteMessage(cliente.getUuid());
        sendMessageToSQS(cliente);
    }

    private void sendMessageToSQS(ClienteFila cliente) {
        try {
            sqsTemplate.send(sqsSendOptions -> sqsSendOptions
                    .queue(QUEUE)
                    .header("uuid", cliente.getUuid())
                    .payload(cliente));
        } catch (Exception ex) {
            log.error("Ao enviar a mensagem ocorreu um erro desconhecido {}", ex.getMessage());
        }
    }

    public void deleteMessage(UUID uuidToDelete) {
        ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest(QUEUE)
                .withMaxNumberOfMessages(10);
        List<com.amazonaws.services.sqs.model.Message> messages = amazonSQS.receiveMessage(receiveRequest).getMessages();
        for (com.amazonaws.services.sqs.model.Message message : messages) {
            ClienteFila cliente = deserializeClienteFromMessage(message.getBody());
            if (cliente != null) {
                String messageUuid = cliente.getUuid().toString();
                if (uuidToDelete.toString().equals(messageUuid)) {
                    String receiptHandle = message.getReceiptHandle();
                    DeleteMessageRequest deleteRequest = new DeleteMessageRequest()
                            .withQueueUrl(QUEUE)
                            .withReceiptHandle(receiptHandle);
                    amazonSQS.deleteMessage(deleteRequest);
                    System.out.println("Message with UUID " + uuidToDelete + " deleted successfully.");
                    break;
                }
            }
        }
    }

    public ClienteFila deserializeClienteFromMessage(String message) {
        try {
            ClienteFila output = objectMapper.readValue(message, ClienteFila.class);
            return output;
        } catch (IOException ex) {
            log.error("Erro ao desserializar a mensagem: {}", ex.getMessage());
            return null;
        }
    }

    public Optional<Message<ClienteFila>> proximo() {
        try {
            Optional<Message<ClienteFila>> receive = sqsTemplate.receive((from -> from.queue(QUEUE).pollTimeout(Duration.ofMillis(3000)).maxNumberOfMessages(1)), ClienteFila.class);
            return receive;
        } catch (Exception ex) {
            log.error("Ocorreu um erro ao chamar a proxima mensagem {}", ex.getMessage());
        }
        return null;
    }
}
