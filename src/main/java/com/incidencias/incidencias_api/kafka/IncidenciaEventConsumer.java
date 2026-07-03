package com.incidencias.incidencias_api.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IncidenciaEventConsumer {
    @KafkaListener(topics = "incidencia-estado-cambiado", groupId = "incidencias-group")
    public void escuchar(String mensaje) {log.info("Incidencia recibida: {}",mensaje);}
}
