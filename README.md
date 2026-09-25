# Incidencias API

API REST para la gestión de incidencias municipales (baches, alumbrado,
limpieza...), con caché distribuida en Redis y notificación de eventos en
tiempo real vía Kafka cada vez que una incidencia cambia de estado.

## Por qué este proyecto

Lo hice para demostrar manejo de tecnologías de mensajería asíncrona y
caché distribuida — habilidades que van más allá de un CRUD típico y que
pocos perfiles junior llegan a tocar en un proyecto propio. La lógica de
negocio (la máquina de estados de una incidencia) vive encapsulada en la
propia entidad, no suelta en el servicio, y está cubierta por tests.

## Funcionalidades

- Gestión de usuarios e incidencias (alta, consulta, actualización, baja)
- Máquina de estados con transiciones controladas: `PENDIENTE → EN_REVISION → EN_PROGRESO → RESUELTA / RECHAZADA` (una transición inválida lanza una excepción, cubierto por tests)
- Consulta de incidencias por distrito
- **Caché con Redis**: las consultas por id y por distrito se cachean (TTL de 10 minutos), con invalidación automática (`@CacheEvict`) al crear, actualizar, eliminar o cambiar el estado de una incidencia — para no servir nunca datos obsoletos
- **Eventos con Kafka**: cada cambio de estado publica un evento en el topic `incidencia-estado-cambiado`, que un consumidor propio recibe y procesa — listo para que otros servicios (notificaciones, auditoría...) se suscriban al mismo topic en el futuro
- Documentación interactiva con Swagger/OpenAPI

## Stack

- Java 21 · Spring Boot · Spring Data JPA
- PostgreSQL
- Redis (caché con Spring Cache)
- Apache Kafka (modo KRaft, sin Zookeeper)
- Docker / Docker Compose
- Lombok · Bean Validation
- JUnit 5 + AssertJ

## Cómo arrancarlo

Todo el stack (base de datos, caché, mensajería y la API) se levanta con un
único comando:

```bash
docker compose up --build
```

Documentación interactiva en `http://localhost:8083/swagger-ui.html`

> Nota: Kafka corre en modo de un único broker (pensado para desarrollo
> local), por lo que el factor de replicación de sus topics internos está
> configurado a 1 en vez del valor por defecto (3), que requeriría varios
> brokers.

## Endpoints principales

| Método | Endpoint                          | Descripción                                  |
|--------|------------------------------------|-----------------------------------------------|
| POST   | `/usuarios`                        | Alta de usuario                               |
| GET    | `/usuarios/{id}`                   | Consulta de usuario (cacheada)                |
| POST   | `/incidencias`                     | Alta de incidencia (estado inicial `PENDIENTE`) |
| GET    | `/incidencias/{id}`                | Consulta de incidencia (cacheada)             |
| GET    | `/incidencias/distrito/{distrito}` | Incidencias de un distrito (cacheada)         |
| PUT    | `/incidencias/{id}`                | Actualiza una incidencia                      |
| PATCH  | `/incidencias/{id}/estado`         | Cambia el estado (publica evento en Kafka)    |
| DELETE | `/incidencias/{id}`                | Elimina una incidencia                        |

## Flujo probado de extremo a extremo

1. Se crea una incidencia → estado `PENDIENTE`
2. Se consulta por id → se cachea en Redis (confirmable viendo que la segunda consulta no genera SQL en los logs)
3. Se cambia el estado con `PATCH /incidencias/{id}/estado` → se guarda en base de datos, se invalida la caché, y se publica un evento en Kafka
4. El consumidor propio recibe el evento (visible en los logs: `Publicando cambio de estado...` seguido de `Incidencia recibida...`)
5. Se vuelve a consultar la incidencia → el estado ya aparece actualizado, sin servir el valor cacheado antiguo

## Tests

`IncidenciaTest` cubre la máquina de estados a nivel de dominio: transición
válida, transición inválida (lanza `IllegalStateException`) y transición
válida desde un estado intermedio.

## Autor

Germán Cabrera Alemán — Backend Developer Java | Spring Boot
[github.com/LagosGame](https://github.com/LagosGame)
