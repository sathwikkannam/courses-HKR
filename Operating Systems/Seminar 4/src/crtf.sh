#!/bin/bash

# Custom command


while(($# > 0))
    do
      if ! [ -f "$1" ];
        then
          echo >> "$1"
          shift
        else
          echo  "$1 already exists."
      fi
    done