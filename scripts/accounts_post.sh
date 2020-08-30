#!/bin/bash
#

show_usage()
{
    echo "Usage:    "`basename $0`" <protocol://host:port> <document_number> <credit_limit>"
    echo "Example:  "`basename $0`" http://127.0.0.1:8080 12345678900 5000.00"
}

if test -z "$1" -o -z "$2" -o -z "$3"
then
    show_usage >&2
    exit 1
fi

BASE_URL="$1"
DOC_NUM="$2"
LIMIT="$3"

URL="${BASE_URL}/accounts"

set -x
curl -q -sSw "\n\nHTTP code: %{http_code}\n" \
-k --ciphers 'DEFAULT:!DH' \
-X POST \
"${URL}" \
-H "Cache-Control: no-cache" \
-H "Content-Type: application/json" \
-d '{ "document_number": "'"${DOC_NUM}"'", "credit_limit": '"${LIMIT}"' }'



# End
