#!/bin/bash
set -e
#gradle clean build --warning-mode all
#gradle clean build --warning-mode none
gradle clean build
java -jar build/libs/JavaUndertow.jar
