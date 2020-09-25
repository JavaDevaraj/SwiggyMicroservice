#!/bin/bash

#############################################
# Build Application using maven   ##
############################################

mvn clean install -DskipTests -f ./swiggy-api-service

mvn clean install -DskipTests -f ./restaurant-partner-api-service

mvn clean install -DskipTests -f ./delivery-agent-api-service

######################################################
# Create Docker Images & Start Application by
#####################################################

sudo docker-compose up 
