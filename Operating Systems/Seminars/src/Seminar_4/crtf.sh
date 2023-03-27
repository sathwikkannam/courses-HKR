#!/bin/bash

# Custom command

for arg in "$@" # Loop through arguments
  do
    if ! [ -f "$arg" ] # If file doesn't exist
      then
        echo >> "$arg"
        echo "$arg file created."
    else
        echo "$arg already exists"
    fi

  done

