
FROM payara/micro
EXPOSE 8080
COPY target/Kwetter.war $DEPLOY_DIR
CMD ["--deploymentDir", "/opt/payara/deployments"]