.PHONY: test
test:
	mvn test

.PHONY: env-up
env-up:
	docker-compose up -d

.PHONY: env-down
env-down:
	docker-compose down

.PHONY: run
run: env-up
	mvn spring-boot:run

.PHONY: clean
clean:
	mvn clean
