#!/bin/bash
set -ex
echo "-"
curl --fail -I -X GET http://0.0.0.0:8080/
echo "-"
curl --fail -I -X GET http://0.0.0.0:8080/health
echo "-"
curl --fail -X GET http://0.0.0.0:8080/status
echo "-"
curl --fail -X GET http://0.0.0.0:8080/about
echo "-"
curl --fail -X GET http://0.0.0.0:8080/add_score/chris/100
echo "-"
curl --fail -X POST http://0.0.0.0:8080/add_score/brian/100
echo "-"
curl --fail -X POST http://0.0.0.0:8080/add/service/deploy-service-v0.0.3/deploy-qa
echo "-"
curl --fail -X POST http://0.0.0.0:8080/add/infrasvc/deploy-infrasvc-v0.0.2/deploy-qa
echo "-"
curl --fail -I -X GET http://0.0.0.0:8080/events
echo "-"
curl --fail -X GET http://0.0.0.0:8080/page_test
echo "-"
echo -e "\nTests Pass"
