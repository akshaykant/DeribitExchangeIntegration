mvn -DskipTests=true  package

docker rm /usertradeservice

docker image build -t usertradeservice .

docker container run -p 8080:8080 --name usertradeservice usertradeservice