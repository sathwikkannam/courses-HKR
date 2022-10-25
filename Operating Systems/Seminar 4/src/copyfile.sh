#!/bin/bash


srcFile=$1
desFile=$2

while read -r line;
  do
    echo "$line" >> "$desFile"
  done <"$srcFile"