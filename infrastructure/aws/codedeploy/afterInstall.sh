#!/bin/bash
sudo systemctl stop tomcat.service

sudo systemctl restart tomcat.service

sudo rm -rf /opt/tomcat/webapps/docs  /opt/tomcat/webapps/examples /opt/tomcat/webapps/host-manager  /opt/tomcat/webapps/manager /opt/tomcat/webapps/ROOT

sudo chown tomcat:tomcat /opt/tomcat/webapps/ROOT.war

# cleanup log files
sudo rm -rf /opt/tomcat/logs/catalina*
sudo rm -rf /opt/tomcat/logs/*.log
sudo rm -rf /opt/tomcat/logs/*.txt

sudo systemctl stop SpringBoot-1.0-SNAPSHOT

sudo mv -f /SpringBoot-1.0-SNAPSHOT.jar /var/SpringBoot-1.0-SNAPSHOT/

sudo chown SpringBoot-1.0-SNAPSHOT:SpringBoot-1.0-SNAPSHOT /var/SpringBoot-1.0-SNAPSHOT/SpringBoot-1.0-SNAPSHOT.jar

sudo chmod 500 /var/SpringBoot-1.0-SNAPSHOT/SpringBoot-1.0-SNAPSHOT.jar

sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/cloudwatch-config.json \
    -s

sudo systemctl enable SpringBoot-1.0-SNAPSHOT

sudo systemctl start SpringBoot-1.0-SNAPSHOT
