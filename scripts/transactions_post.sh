#!/bin/bash
#

show_usage()
{
    echo "Usage:    "`basename $0`" <protocol://host:port> <account_id> <operation_type_id> <amount>"
    echo "Example:  "`basename $0`" http://127.0.0.1:8080 1 4 123.45"
}

if test -z "$1" -o -z "$2" -o -z "$3" -o -z "$4"
then
    show_usage >&2
    exit 1
fi

BASE_URL="$1"
ACC_ID="$2"
OPE_TID="$3"
AMOUNT="$4"

URL="${BASE_URL}/transactions"

set -x
curl -q -sSw "\n\nHTTP code: %{http_code}\n" \
-k --ciphers 'DEFAULT:!DH' \
-X POST \
"${URL}" \
-H "Cache-Control: no-cache" \
-H "Content-Type: application/json" \
-d '{ "account_id": '"${ACC_ID}"', "operation_type_id": '"${OPE_TID}"', "amount": '"${AMOUNT}"' }'



# End
