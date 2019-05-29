#!/bin/bash
sleep 40
java -jar payara-micro.jar --deploymentDir /opt/payara/deployments --domainConfig /opt/payara/domain.xml --addJars /opt/payara/mysql-connector-java-8.0.15.jar
