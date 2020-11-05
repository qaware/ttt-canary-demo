#!/usr/bin/env bash

set -euo pipefail

eval "$(minikube -p minikube docker-env)"

docker build -t canary-proxy:1 .
