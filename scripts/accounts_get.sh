#!/bin/bash
#

show_usage()
{
    echo "Usage:    "`basename $0`" <protocol://host:port> <accountId>"
    echo "Example:  "`basename $0`" http://127.0.0.1:8080 1"
}

if test -z "$1" -o -z "$2"
then
    show_usage >&2
    exit 1
fi

BASE_URL="$1"
ACC_ID="$2"

URL="${BASE_URL}/accounts/${ACC_ID}"

set -x
curl -q -sSw "\n\nHTTP code: %{http_code}\n" \
-k --ciphers 'DEFAULT:!DH' \
-X GET \
"${URL}" \
-H "Cache-Control: no-cache" \
-H "accept: application/json"



# End
