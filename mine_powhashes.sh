#!/bin/bash

# Generates some load 

[ -z "$FAAS_IP" ] && echo "Need to set FAAS_IP" && exit 1;

GATEWAY_PORT=80

echo "Using ${FAAS_IP}:${GATEWAY_PORT} as OpenFaaS Gateway"

invoke_hash() {
  random=$(cat /dev/urandom | env LC_CTYPE=C tr -dc a-zA-Z0-9 | head -c 16; echo)
  echo "Searching hash for ${random}" 
  curl --no-keepalive http://${FAAS_IP}:${GATEWAY_PORT}/function/qpowhash/${random}
  echo ""
}
export -f invoke_hash

start_sleep=.4

for i in {1..2000}
do
  invoke_hash &
  sleep $(bc <<< "scale=6;$start_sleep - $i * 0.0005" )
done
wait
echo "FINISHED"
