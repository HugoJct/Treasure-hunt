#if no recipe is specified, this one will apply
default: clearT run

#the build and run recipes for the whole project
build:
	./gradlew build
run: build
	./gradlew run

#the build and run recipes for the client side of the project only
client: clearT buildClient
	./gradlew :client:run --console=plain
buildClient:
	./gradlew :client:build

#run the client only (without build)
runclient: clearT 
	./gradlew :client:run --console=plain

#the build and run recipes for the server side of the project only
server: clearT buildServer
	./gradlew :server:run --console=plain
buildServer:
	./gradlew :server:build

#run the server only (without build)
runserver: clearT
	./gradlew :server:run --console=plain

#convenience recipes
clearT:
	clear
clean:
	rm -r build/classes