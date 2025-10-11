# Monolith-to-Microservice E‑commerce with Spring Boot

A starting point for building an e‑commerce application and evolving it from a monolith to microservices using Spring Boot.

This repository currently contains a minimal Spring Boot skeleton to get you running quickly. As the project grows, we’ll introduce modularization, service boundaries, and communication patterns typical of microservice architectures.

## Prerequisites

- Java Development Kit (JDK) matching the Maven compiler settings in `pom.xml`.
	- Current config: `java.version=25` (source/target 25)
	- If you don’t have JDK 25 installed, you can either install it or change the values in `pom.xml` (for example, 17 or 21) to match your installed JDK.
- No global Maven required — the project includes the Maven Wrapper.

## Quick start (Windows PowerShell)

From the project root (`ecom-application`):

```powershell
# 1) Verify Maven Wrapper works and prints Maven version
./mvnw.cmd -v

# 2) Run the application (dev mode)
./mvnw.cmd spring-boot:run

# The app starts on http://localhost:8080 by default
```

To build a runnable JAR:

```powershell
./mvnw.cmd clean package

# Run the packaged app
java -jar target/ecom-application-0.0.1-SNAPSHOT.jar
```

## Project structure (current)

```
src/
	main/
		java/
			com/app/ecom/
				EcomApplication.java      # Spring Boot entry point
				UserController.java       # Placeholder controller (to be implemented)
		resources/
			application.properties      # Spring Boot configuration
	test/
		java/
			com/app/ecom/
				EcomApplicationTests.java # Placeholder test
pom.xml                           # Build config and dependencies
mvnw / mvnw.cmd                    # Maven Wrapper scripts
```

## Testing

```powershell
./mvnw.cmd test
```

## Configuration

- Default settings live in `src/main/resources/application.properties`.
- Common tweaks:
	- Change server port: `server.port=8081`
	- Set active profile: `spring.profiles.active=dev`

## Troubleshooting

- Build fails with “release version X not supported” or similar:
	- Your installed JDK likely doesn’t match the `java.version`/`maven.compiler.*` in `pom.xml`.
	- Either install the matching JDK or update `pom.xml` values (for example, to 17 or 21) to align with your JDK.
- Port already in use:
	- Adjust `server.port` in `application.properties` or stop the conflicting process.

## Roadmap (monolith → microservices)

- Define domain modules (catalog, cart, orders, payments, users)
- Introduce persistence (Spring Data JPA), migrations (Flyway), and seed data
- Add REST controllers and DTOs
- Add authentication/authorization (Spring Security)
- Extract domains into independent services
- Service-to-service communication (REST/Feign, messaging)
- Observability (Actuator, logs, tracing), resilience patterns
- CI/CD pipeline and containerization

## Contributing

Issues and pull requests are welcome. Please create a feature branch from `main` and open a PR.

## License

This project has not specified a license. If you plan to share or reuse, consider adding a LICENSE file (e.g., MIT, Apache-2.0).

