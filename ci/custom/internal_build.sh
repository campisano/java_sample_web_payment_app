#!/usr/bin/env bash

set -x -o errexit -o nounset -o pipefail

export DEBIAN_FRONTEND=noninteractive

rm -f /etc/apt/apt.conf.d/docker*
apt-get -qq -y update
apt-get -qq -y install --no-install-recommends apt-utils > /dev/null
apt-get -qq -y install --no-install-recommends wget tar gzip > /dev/null

./ci/custom/install_maven.sh
mvn -B -ntp clean test package spring-boot:repackage

PACKAGE="$(mvn -B -q help:evaluate -Dexpression=project.build.finalName -DforceStdout).jar"
FOLDER="$(mvn -B -q help:evaluate -Dexpression=project.build.directory -DforceStdout)"
cp -a "${FOLDER}/${PACKAGE}" app.jar
