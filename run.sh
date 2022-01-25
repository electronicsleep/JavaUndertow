#!/bin/bash
set -e
gradle clean build
java -jar build/libs/JavaUndertow.jar
