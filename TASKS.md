# Tasks

## 1. Build foundation

Goal: create a runnable Java 21 Spring Boot project.

Required test: application context loads.

Completion criterion: `./gradlew test` passes.

## 2. Domain model

Goal: model wallet, transaction, identity, money, and transaction status without framework dependencies.

Required test: domain restoration and validation tests.

Completion criterion: domain values preserve their invariants and equality semantics.

## 3. Persistence adapter

Goal: persist wallets and transactions through Flyway, JPA, and repository ports.

Required test: PostgreSQL adapter round-trip integration test.

Completion criterion: a saved wallet is restored with its ID, user ID, balance, and version.

## 4. HTTP API

Goal: expose wallet capabilities through a REST interface.

Required test: controller integration tests.

Completion criterion: HTTP endpoints are documented and verified.

## 5. Transfer workflow

Goal: coordinate wallet debits, credits, and transaction records.

Required test: transfer use-case tests.

Completion criterion: successful and rejected transfers preserve wallet invariants.

## 6. Concurrency control

Goal: protect transfer updates with transactions and locking.

Required test: concurrent transfer integration tests.

Completion criterion: concurrent updates cannot lose money or corrupt versions.

## 7. Eventing and resilience

Goal: add Redis, RabbitMQ, bank integrations, and resilience policies where needed.

Required test: integration and failure-handling tests.

Completion criterion: external failures are isolated and events are delivered reliably.

## 8. Deposit strategies

Goal: add supported deposit methods as explicit strategies.

Required test: strategy contract tests.

Completion criterion: each approved deposit method conforms to the shared behavior.
