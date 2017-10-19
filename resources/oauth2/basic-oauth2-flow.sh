#!/bin/bash

curl   \
  -X POST \
  -u lab-insurance-mobile:secret \
  -d grant_type=password \
  -d client_id=lab-insurance-mobile \
  -d username=user1 \
  -d password=user1 \
  -v \
  http://localhost:8060/oauth/token
