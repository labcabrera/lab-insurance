#!/bin/bash

HOST="localhost"
PORT="8888"
CONFIG_NAME=$1
PROFILE=$2

if [ -z "$1" ]; then
    CONFIG_NAME="insurance-common"
fi

if [ -z "$2" ]; then
    PROFILE="default"
fi

URL="http://${HOST}:${PORT}/${CONFIG_NAME}/${PROFILE}"

echo ${URL}

curl -s ${URL} | python -m json.tool

