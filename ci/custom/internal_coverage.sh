#!/usr/bin/env bash

set -x -o errexit -o nounset -o pipefail

TOKEN="$1"

export DEBIAN_FRONTEND=noninteractive
apt-get -qq -y update
apt-get -qq -y install --no-install-recommends apt-utils > /dev/null
apt-get -qq -y install wget git > /dev/null

wget -c -nv --no-check-certificate -O codecov https://codecov.io/bash
bash ./codecov -X coveragepy -X xcode -s ./target -t "${TOKEN}"
