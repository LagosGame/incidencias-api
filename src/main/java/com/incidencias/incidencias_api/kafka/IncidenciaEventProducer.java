package com.incidencias.incidencias_api.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class IncidenciaEventProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    private static final String TOPIC = "incidencia-estado-cambiado";

    public void publicarCambioEstado(Long id,String estado){
        String mensaje = "Incidencia " + id + " cambió ha estado : " + estado;
        log.info("Publicando cambio de estado en topic {}: {}", TOPIC, mensaje);
        kafkaTemplate.send(TOPIC,mensaje);
    }

}
