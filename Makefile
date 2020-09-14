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

.PHONY: format
format:
	command -v astyle >/dev/null 2>&1 \
&& astyle --options=none --quiet --style=java --mode=java \
--indent=spaces=4 --lineend=linux --align-pointer=middle \
--pad-oper --pad-comma --unpad-paren \
--add-brackets --convert-tabs --max-code-length=80 \
--suffix=none --recursive --suffix=none src/*.java --preserve-date \
|| echo "Warning: can't find astyle executable"
