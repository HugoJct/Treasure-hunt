default: clearT run
build:
	./gradlew build
run: build
	./gradlew run
clearT:
	clear
clean:
	rm -r build/classes