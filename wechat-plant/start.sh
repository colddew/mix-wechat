#!/bin/sh
PROCESS_COUNT=`ps aux | grep wechat | grep -v grep | wc -l`

if [ $PROCESS_COUNT -gt 0 ]; then
  ps aux | grep wechat | grep -v grep | awk '{print $2}' | xargs kill -9
fi

nohup java -jar wechat-plant.jar &
