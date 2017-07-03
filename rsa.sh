#!/usr/bin/env bash

openssl genrsa -out private.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -in private.pem -out private.p8 -nocrypt
openssl rsa -inform PEM -outform PEM -in private.pem -out public.pem -pubout
