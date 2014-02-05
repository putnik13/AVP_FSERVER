#!/bin/bash

export JETTY_HOME=/home/Installed/Jetty-8.1.14.v20131031
echo Jetty Home: $JETTY_HOME

cd $JETTY_HOME
java -jar start.jar