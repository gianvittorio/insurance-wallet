#!/bin/bash

docker-compose down && docker volume rm -f $(docker volume ls --filter name=insurance-wallet) && \
  mvn clean
