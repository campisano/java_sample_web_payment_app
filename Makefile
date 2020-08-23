.PHONY: test
test:
	mvn test

.PHONY: run
run:
	mvn spring-boot:run

.PHONY: run-env
run-env: env-up run

.PHONY: run-reset
run-reset: env-down run-env

.PHONY: env-up
env-up:
	docker-compose up -d

.PHONY: env-down
env-down:
	docker-compose down

.PHONY: clean
clean:
	mvn clean
