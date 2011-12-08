#!/bin/bash

if [ "`uname -s`" = "Darwin" ]; then
	NATIVES="natives/macosx"
else
	NATIVES="natives/linux"
fi

java -Djava.library.path=$NATIVES -jar "Best Game.jar"