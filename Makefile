#if no recipe is specified, this one will apply
default: clearT run

#the build and run recipes for the whole project
build:
	./gradlew build
run: build
	./gradlew run

#the build and run recipes for the client side of the project only
client: buildClient
	./gradlew :client:run
buildClient:
	./gradlew :client:build

#the build and run recipes for the server side of the project only
server: buildServer
	./gradlew :server:run
buildServer:
	./gradlew :server:build

#convenience recipes
clearT:
	clear
clean:
	rm -r build/classes