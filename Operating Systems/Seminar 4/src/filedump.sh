#!/bin/bash


file=$1


if [ -f "$file" ];
  then
    while read -r line;
      do
        echo -e "$line"
      done <"$file"
  else
    echo "$file doesn't exist"
fi