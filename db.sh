#!/bin/bash

docker run --name postgres -e POSTGRES_PASSWORD=testPassword -e POSTGRES_USER=testUser -e POSTGRES_DB=testdb -p 5432:5432 -d postgres