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
curl --fail -X POST http://0.0.0.0:8080/add_score/chris/100
echo "-"
#curl --fail -X POST http://0.0.0.0:8080/add -H 'Content-Type: application/json' -d '{"service":"infrasvc","event":"deploy-infrasvc-v0.0.2", "event_type":"deploy-qa"}'
#echo "-"
#curl --fail -I -X GET http://0.0.0.0:8080/events
#echo "-"
curl --fail -X GET http://0.0.0.0:8080/page_test
echo "-"
echo -e "\ntests pass"
