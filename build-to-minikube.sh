#!/usr/bin/env bash

set -euo pipefail

if [ -z ${VERSION+x} ]; then echo "VERSION is not set!"; exit 1; fi

if [ -z ${FAILURE+x} ]; then echo "FAILURE is not set!"; exit 1; fi

mvn clean package

eval "$(minikube -p minikube docker-env)"
docker build --build-arg APPLICATION_VERSION="$VERSION" --build-arg APPLICATION_FAILURE_PERCENTAGE="$FAILURE"  -t canary-demo:"$VERSION" .
