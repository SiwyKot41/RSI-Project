#!/bin/bash

INSTANCE_NAME=wsdl--main--gf--wvffle
SERVICE=ImageService

# Generate server war file
./mvnw clean install war:war exec:exec
if [ $? -ne 0 ]; then
    echo "Failed to build or deploy war file"
    exit 1
fi

sleep 0.1

wget https://$INSTANCE_NAME.code.wvffle.net/RSI/$SERVICE?wsdl -O - | sed 's/http:\/\/'$INSTANCE_NAME'.code.wvffle.net:80/https:\/\/'$INSTANCE_NAME'.code.wvffle.net\//g' > src/main/resources/service.wsdl

mkdir -p src/main/java/net/wvffle/rsi/client/generated
wsimport -J-Djavax.xml.accessExternalSchema=all src/main/resources/service.wsdl -keep -p net.wvffle.rsi.client.generated -d src/main/java -wsdllocation /service.wsdl

# Generate client Jar file
./mvnw install