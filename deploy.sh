#!/bin/bash

INSTANCE_NAME=wsdl--main--rsi-06--siwykot41
SERVICE=ImageService
PACKAGE=org.fr.rsi

# Generate server war file
./mvnw clean install war:war exec:exec
if [ $? -ne 0 ]; then
    echo "Failed to build or deploy war file"
    exit 1
fi

sleep 0.1

wget https://$INSTANCE_NAME.code.wvffle.net/RSI/$SERVICE?wsdl -O - | sed 's/http:\/\/'$INSTANCE_NAME'.code.wvffle.net:80/https:\/\/'$INSTANCE_NAME'.code.wvffle.net\//g' > src/main/resources/service.wsdl

PACKAGE_DIR="$(echo $PACKAGE | sed 's/\./\//g')"

mkdir -p src/main/java/$PACKAGE_DIR/client/generated
wsimport -J-Djavax.xml.accessExternalSchema=all src/main/resources/service.wsdl -keep -p $PACKAGE.client.generated -d src/main/java -wsdllocation /service.wsdl

# Generate client Jar file
./mvnw install