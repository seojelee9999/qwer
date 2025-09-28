#!/bin/bash

docker run -d \
  --name local_db \
  -e POSTGRES_USER=devuser \
  -e POSTGRES_PASSWORD=devpass \
  -e POSTGRES_DB=local_db \
  -p 5432:5432 \
  -v pgdata:/var/lib/postgresql/data \
  postgres:14

