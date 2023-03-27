#!/bin/bash


srcFile=$1
desFile=$2


if [ -f "$srcFile" ] # Source file must exist for copying, the destination file will be created if it doesn't exist.
  then
    while read -r line;
      do
        echo "$line" >> "$desFile"
      done <"$srcFile"
  else
    echo "$srcFile doesn't exist"
    exit
fi