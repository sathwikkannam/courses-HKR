#!/bin/bash


srcFile=$1
desFile=$2


if [ -f "$srcFile" ] || [ -f "$desFile" ];
  then
    while read -r line;
      do
        echo -e "$line"
      done <"$srcFile"
  else
    echo "One of the files doesn't exist"
fi
