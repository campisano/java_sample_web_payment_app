.PHONY: test
test:
	mvn test

.PHONY: run
run:
	mvn spring-boot:run

.PHONY: clean
clean:
	mvn clean
