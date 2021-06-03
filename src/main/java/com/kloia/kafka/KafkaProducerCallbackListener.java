package com.kloia.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Slf4j
@Component
public class KafkaProducerCallbackListener<K, V> implements ListenableFutureCallback<SendResult<K, V>> {

    @Override
    public void onFailure(Throwable throwable) {
        log.error("Error sending message: {}", throwable.getMessage(), throwable);
    }

    @Override
    public void onSuccess(SendResult<K, V> sendResult) {
        if (sendResult != null
                && sendResult.getProducerRecord() != null
                && sendResult.getProducerRecord().value() != null) {

            String topic = sendResult.getProducerRecord().topic();
            log.info("Successful produce! Topic: {} Key: {} Data: {}",
                    topic, sendResult.getProducerRecord().key(), sendResult.getProducerRecord().value()
            );

        } else {
            log.info("Successful produce but not enough information!)");
        }
    }
}
