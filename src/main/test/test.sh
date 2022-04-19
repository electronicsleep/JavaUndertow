#!/bin/bash
set -ex

curl --fail -X GET http://0.0.0.0:8080/
echo "-"
curl --fail -X GET http://0.0.0.0:8080/about
echo "-"
curl --fail -X GET http://0.0.0.0:8080/status
echo "-"
curl --fail -X GET http://0.0.0.0:8080/health
echo "-"
curl --fail -X POST http://0.0.0.0:8080/about
echo "-"
curl --fail -X GET http://0.0.0.0:8080/add_score/chris/100
echo "-"
curl --fail -X POST http://0.0.0.0:8080/add_score/chris/100

echo -e "\ntests pass"
