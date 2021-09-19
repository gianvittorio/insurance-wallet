#!/bin/bash

docker-compose down && \
  docker volume rm -f $(docker volume ls --filter=insurance-wallet)
