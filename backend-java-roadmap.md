# Guía de Estudio: Backend con Java
## Enfoque Top-Down para QA Automation → Backend Developer

---

## Filosofía de esta guía

Esta guía sigue un enfoque **top-down**: empezamos construyendo cosas funcionales desde el día uno, y vamos profundizando en los detalles conforme avanzamos. La idea es mantener la motivación alta viendo resultados tangibles, mientras desarrollamos intuición sobre cómo encajan las piezas.

---

## Fase 1: Tu primer servicio funcionando
**Objetivo:** Tener un API REST desplegada en menos de una semana

### 1.1 Spring Boot desde cero
- Crear un proyecto con [Spring Initializr](https://start.spring.io/)
- Dependencias iniciales: Spring Web, Spring Data JPA, H2 Database
- Entender la estructura de un proyecto Spring Boot
- Tu primer `@RestController` con endpoints GET/POST

### 1.2 Primer CRUD completo
- Crear una entidad simple (ej: `Task`, `Product`, `User`)
- Repository con Spring Data JPA
- Endpoints básicos: listar, crear, actualizar, eliminar
- Probar con Postman o curl

### 1.3 Base de datos real
- Cambiar de H2 a PostgreSQL
- Configuración en `application.properties`
- Docker Compose para levantar PostgreSQL localmente

**Proyecto práctico:** API de gestión de tareas (To-Do List)

---

## Fase 2: Construyendo APIs profesionales
**Objetivo:** Aplicar patrones y prácticas de la industria

### 2.1 Arquitectura en capas
- Controller → Service → Repository
- DTOs vs Entidades (por qué separar)
- Mapeo con MapStruct o manual

### 2.2 Validación y manejo de errores
- Bean Validation (`@Valid`, `@NotNull`, `@Size`)
- `@ControllerAdvice` para errores globales
- Respuestas de error consistentes (RFC 7807)

### 2.3 Documentación de APIs
- OpenAPI/Swagger con SpringDoc
- Documentar endpoints automáticamente
- Swagger UI para probar la API

### 2.4 Testing de APIs
- Tests de integración con `@SpringBootTest`
- MockMvc para probar controllers
- Testcontainers para tests con BD real

**Proyecto práctico:** API de biblioteca (libros, autores, préstamos)

---

## Fase 3: Profundizando en Java
**Objetivo:** Dominar el lenguaje que sustenta todo

### 3.1 Java Core esencial
- Tipos de datos, colecciones (List, Set, Map)
- Streams y programación funcional
- Optional y manejo de nulls
- Records (Java 17+)

### 3.2 Orientación a objetos aplicada
- Herencia vs Composición
- Interfaces y su uso real
- Patrones comunes: Builder, Factory, Strategy

### 3.3 Concurrencia básica
- Threads y ExecutorService
- CompletableFuture para async
- Problemas comunes y cómo evitarlos

### 3.4 Gestión de dependencias
- Maven: pom.xml, ciclo de vida, plugins
- Entender el classpath
- Gestión de versiones

**Ejercicios:** Katas de código, refactorizar código legacy

---

## Fase 4: Persistencia avanzada
**Objetivo:** Dominar el acceso a datos

### 4.1 SQL sólido
- Joins, subqueries, agregaciones
- Índices y su impacto en rendimiento
- Window functions
- EXPLAIN ANALYZE

### 4.2 JPA/Hibernate en profundidad
- Ciclo de vida de entidades
- Relaciones: @OneToMany, @ManyToMany
- Lazy vs Eager loading
- N+1 problem y cómo resolverlo

### 4.3 Queries avanzadas
- JPQL y Criteria API
- Projections y DTOs desde queries
- Paginación y ordenación

### 4.4 Transacciones
- `@Transactional` y sus atributos
- Propagación y aislamiento
- Manejo de errores transaccionales

**Proyecto práctico:** Sistema de e-commerce (productos, pedidos, inventario)

---

## Fase 5: Seguridad
**Objetivo:** Proteger tus aplicaciones

### 5.1 Spring Security fundamentos
- Autenticación vs Autorización
- Configuración básica de seguridad
- Filtros y cadena de seguridad

### 5.2 Autenticación con JWT
- Cómo funcionan los tokens JWT
- Implementar login y registro
- Refresh tokens

### 5.3 Autorización
- Roles y permisos
- `@PreAuthorize` y expresiones SpEL
- Seguridad a nivel de método

**Proyecto práctico:** Añadir autenticación al e-commerce

---

## Fase 6: Arquitectura y patrones
**Objetivo:** Diseñar sistemas escalables

### 6.1 Principios SOLID aplicados
- Ejemplos reales en Spring
- Refactoring hacia SOLID

### 6.2 Clean Architecture
- Separación de responsabilidades
- Inversión de dependencias
- Puertos y adaptadores (Hexagonal)

### 6.3 Patrones de diseño útiles
- Repository, Service, Factory
- Strategy para lógica variable
- Observer/Events en Spring

### 6.4 Diseño de APIs REST
- Versionado de APIs
- HATEOAS (cuándo sí, cuándo no)
- Rate limiting

**Proyecto práctico:** Rediseñar proyectos anteriores aplicando estos principios

---

## Fase 7: Comunicación entre servicios
**Objetivo:** Ir más allá del monolito

### 7.1 Llamadas HTTP entre servicios
- RestTemplate vs WebClient
- Manejo de errores y reintentos
- Circuit breaker con Resilience4j

### 7.2 Mensajería asíncrona
- Conceptos: colas, topics, producers, consumers
- RabbitMQ o Kafka (elegir uno para empezar)
- Spring Cloud Stream

### 7.3 Event-Driven Architecture
- Eventos de dominio
- Event sourcing (conceptos)
- CQRS (conceptos)

**Proyecto práctico:** Dividir el e-commerce en servicios (pedidos, inventario, notificaciones)

---

## Fase 8: Observabilidad y operaciones
**Objetivo:** Saber qué pasa en producción

### 8.1 Logging estructurado
- SLF4J y Logback
- Niveles de log y cuándo usarlos
- Correlation IDs

### 8.2 Métricas
- Micrometer y Actuator
- Métricas de negocio vs técnicas
- Dashboards con Grafana

### 8.3 Tracing distribuido
- OpenTelemetry
- Rastrear requests entre servicios

### 8.4 Health checks y probes
- Liveness vs Readiness
- Actuator endpoints

**Proyecto práctico:** Instrumentar servicios anteriores

---

## Fase 9: Despliegue y DevOps
**Objetivo:** Llevar código a producción

### 9.1 Containerización
- Dockerfile para aplicaciones Spring
- Multi-stage builds
- Docker Compose para desarrollo

### 9.2 CI/CD
- GitHub Actions o GitLab CI
- Pipeline: build, test, deploy
- Análisis de código (SonarQube)

### 9.3 Kubernetes conceptos
- Pods, Deployments, Services
- ConfigMaps y Secrets
- Helm charts básicos

### 9.4 Cloud (elegir uno)
- AWS: EC2, RDS, ECS/EKS
- GCP: Cloud Run, Cloud SQL
- Infraestructura como código (Terraform basics)

**Proyecto práctico:** Desplegar el e-commerce en la nube

---

## Fase 10: Temas avanzados
**Objetivo:** Especialización y profundización

### 10.1 Performance
- Profiling de aplicaciones
- Caching con Redis
- Connection pooling (HikariCP)
- Garbage Collection tuning

### 10.2 Reactive programming (opcional)
- Project Reactor
- WebFlux
- R2DBC

### 10.3 GraphQL (opcional)
- Spring for GraphQL
- Cuándo REST vs GraphQL

---

## Recursos recomendados

### Libros
1. **"Head First Java"** - Para reforzar fundamentos
2. **"Spring in Action"** - Spring completo
3. **"Effective Java"** - Cuando tengas soltura con Java
4. **"Designing Data-Intensive Applications"** - Sistemas distribuidos

### Cursos
- Baeldung (tutoriales y guías Spring)
- Java Brains (YouTube)
- Amigoscode (YouTube)

### Práctica
- LeetCode (algoritmos, dificultad Easy/Medium)
- Exercism (track de Java)
- Proyectos personales (lo más importante)

---

## Consejos finales

1. **No te atasques en la teoría**: Si algo no tiene sentido, sigue adelante y vuelve después. Muchas cosas "click" cuando las ves en contexto.

2. **Construye proyectos reales**: La mejor forma de aprender es haciendo. Un proyecto feo pero funcional vale más que diez tutoriales.

3. **Lee código de otros**: Explora repos open source de Spring. Ver cómo otros resuelven problemas es muy valioso.

4. **Aprovecha tu background de QA**: Ya sabes pensar en edge cases, testing, y calidad. Eso es una ventaja enorme.

5. **No intentes aprender todo a la vez**: Esta guía es un mapa, no una carrera. Ve a tu ritmo.

---

*Última actualización: Enero 2025*
*Creado para la transición QA → Backend Developer*
