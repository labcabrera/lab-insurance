#!/bin/bash

curl -s curl http://localhost:8080/api/assets/search/isin/ASSET01 | python -m json.tool

