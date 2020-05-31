#!/bin/zsh

emulate -LR zsh


function horizontal_line() {
  echo "================================================================================"
}

#./gradlew clean bootJar

# self-signed 인증서 사용 시 k6 스크립트에서 다음 에러 발생하므로 non-TLS 로 테스트
#WARN[0012] Request Failed                                error="Post \"https://localhost:8443/v1/sellers\": x509: certificate is not valid for any names, but wanted to match localhost"
PROTOCOL="http"
HOST="localhost"
PORT=8080


horizontal_line
echo "Stopping old container if exists"
CONTAINER_NAME="monolith-simple-mall"
OLD_CONTAINER_ID="$(docker ps | grep $CONTAINER_NAME | awk '{print $1}')"
if [ -n "$OLD_CONTAINER_ID" ]
then
  docker stop "$OLD_CONTAINER_ID"
  echo "Container $OLD_CONTAINER_ID stopped."
else
  echo "No containers with name [$CONTAINER_NAME]"
fi


horizontal_line
echo "Removing old image if exists"
IMAGE_NAME="monolith-simple-mall"
IMAGE_ID=$(docker images -q $IMAGE_NAME | uniq)
if [ -n "$IMAGE_ID" ]
then
  echo "Removing image [$IMAGE_NAME:$IMAGE_ID]"
  docker rmi --force "$IMAGE_ID"
else
  echo "No image named [$IMAGE_NAME]"
fi


horizontal_line
echo "Creating image [$IMAGE_NAME]"
docker build -t $IMAGE_NAME .


horizontal_line
echo "Building and executing container [$CONTAINER_NAME] from image [$IMAGE_NAME]"
PROFILE_NAME="docker-non-tls"
CONTAINER_ID=$(docker run -d --name $CONTAINER_NAME --rm -p$PORT:$PORT -e "SPRING_PROFILES_ACTIVE=$PROFILE_NAME" $IMAGE_NAME)


horizontal_line
echo "Sleeping 5s for server up"
sleep 5s
function check_server() {
  SERVER_STATUS=$(curl -sSk $PROTOCOL://$HOST:$PORT/actuator/health)
  if [ "$SERVER_STATUS" = "{\"status\":\"UP\"}" ]
  then
    return 0
  else
    echo "Not up yet"
    return 1
  fi
}
n=0
until check_server
do
  n=$((n + 1))
  if [ "$n" = 20 ]
  then
    echo "Give up retrying. Test stopped."
    exit 1
  else
    sleep 3s
    echo "Retry #$n"
  fi
done
echo "Server is UP!!! Ready to Rock!!!"


horizontal_line
echo "Creating Sellers"
k6 run ./k6-create-sellers.js


horizontal_line
echo "All tests are done. Stopping container [$CONTAINER_NAME:$CONTAINER_ID]"
docker stop "$CONTAINER_ID"
horizontal_line

