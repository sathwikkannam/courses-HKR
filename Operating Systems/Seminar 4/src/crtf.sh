#!/bin/bash

# Custom command

for arg in "$@"
  do
    if ! [ -f "$arg" ]
      then
        echo >> "$arg"
        echo "$arg file created."
    else
        echo "$arg already exists"
    fi

  done

