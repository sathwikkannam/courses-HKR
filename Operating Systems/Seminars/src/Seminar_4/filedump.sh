#!/bin/bash


file=$1


if [ -f "$file" ]; # if source file exists.
  then
    while read -r line; # Read mode.
      do
        echo "$line" # Display every line to console
      done <"$file" # File input
  else
    echo "$file doesn't exist"
fi