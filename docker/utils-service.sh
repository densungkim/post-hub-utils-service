#!/usr/bin/env bash

PROFILE=${PROFILE:-local}

echo "Starting service with profile: $PROFILE"
exec java -jar /srv/utils-service-0.0.1-SNAPSHOT.jar --spring.profiles.active="$PROFILE"
