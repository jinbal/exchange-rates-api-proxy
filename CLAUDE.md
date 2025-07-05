# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

This is a Scala-based HTTP4s REST API proxy for currency exchange rates. It provides a single endpoint that converts currency amounts using real-time exchange rates from the Coinbase API, with 60-second caching implemented using Caffeine.

## Build and Test Commands

```bash
# Run unit tests
sbt clean test

# Run integration tests
sbt clean it/test

# Build fat JAR
sbt assembly

# Run the application
java -jar target/scala-2.13/exchange-rates-api-proxy-assembly-0.1.jar
```

## Architecture

### Core Components

- **Main.scala**: Application entry point using IOApp
- **ExchangeRatesServer.scala**: HTTP server setup with Blaze server builder, binds to localhost:8080
- **ExchangeRatesRoutes.scala**: REST endpoint definition for `/convert` with query parameters
- **CurrencyConverter.scala**: Business logic for currency conversion calculations
- **ExchangeRateApiClient.scala**: HTTP client for Coinbase API integration
- **CachingExchangeRateApiClient.scala**: Caching wrapper using Caffeine with 60-second TTL
- **ExchangeRatesDomain.scala**: Domain models and custom exceptions

### API Endpoint

```
GET /convert?fromCurrency=GBP&toCurrency=EUR&amount=102.6
```

Returns JSON with exchange rate, converted amount, and original amount.

### Technology Stack

- **HTTP4s**: Web framework with Blaze server/client
- **Cats Effect**: Functional effect system
- **Circe**: JSON serialization/deserialization
- **ScalaCache + Caffeine**: Caching layer
- **MUnit + ScalaTest**: Testing frameworks
- **REST Assured**: Integration testing

### Key Design Patterns

- Repository pattern for API client abstraction
- Dependency injection through constructor parameters
- Immutable domain models
- Functional error handling with IO monad
- Caching decorator pattern for performance optimization

## Testing

- Unit tests in `src/test/scala/`
- Integration tests in `src/it/scala/`
- Uses MUnit, ScalaTest, and REST Assured for different test scenarios
- Separate test configuration for integration tests

## Native Image Support

GraalVM native image compilation is supported with reflection configuration in `src/main/resources/META-INF/native-image/`.