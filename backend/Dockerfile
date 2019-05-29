
FROM payara/micro

COPY target/Kwetter.war $DEPLOY_DIR
COPY --chown=payara:payara mysql-connector-java-8.0.15.jar /opt/payara/mysql-connector-java-8.0.15.jar
COPY --chown=payara:payara domain.xml /opt/payara/domain.xml
#CMD ["--deploymentDir", "/opt/payara/deployments", "--domainConfig", "/opt/payara/domain.xml"]
CMD ["--deploymentDir", "/opt/payara/deployments", "--domainConfig", "/opt/payara/domain.xml", "--addJars", "/opt/payara/mysql-connector-java-8.0.15.jar"]