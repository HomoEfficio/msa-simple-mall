#!/bin/sh

java --version
SPRING_PROFILES_ACTIVE=docker java -jar app.jar
