#!/bin/bash


srcFile=$1
desFile=$2


if [ -f "$srcFile" ] || [ -f "$desFile" ];
  then
    while read -r line;
      do
        echo "$line" >> "$desFile"
      done <"$srcFile"
  else
    if ! [ -f "$desFile" ]
      then
        echo >> "$desFile"
    fi
fi
