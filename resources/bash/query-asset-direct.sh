#!/bin/bash

curl -s http://localhost:8093/search/isin/ASSET01 | python -m json.tool

