#! /bin/bash

help()
{
	echo
	echo  "usage : mvno-batch.sh [start,stop,restart,status] [serverType:local|dev|live]"
	echo  "ex] mvno-batch.sh start live"
	echo
	
	exit 1;
}

if [ "$1" == "" ]; then
		help
fi

if [ "$1" == "start" ] || [ "$1" == "restart" ]; then
		if [ "$2" == "" ] || [ "$2" != "local" ] && [ "$2" != "dev" ] && [ "$2" != "live" ]; then
				help
		fi
fi

if [ "$1" != "start" ] && [ "$1" != "stop" ] && [ "$1" != "restart" ] && [ "$1" != "status" ]; then
		help
fi

SERVER_TYPE=$2

SERVICE_NAME=MvnoBatch_Schedule

echo "- SERVICE NAME : $SERVICE_NAME"
echo "- SERVER TYPE : $SERVER_TYPE"

# Set JAVA_HOME
#JAVA_HOME=$JAVA_HOME
echo JAVA_HOME: $JAVA_HOME


start()
{
	echo "Starting $SERVICE_NAME ............"
	$JAVA_HOME/bin/java -D${SERVICE_NAME} -jar ktmmBatch.jar ${SERVER_TYPE} schedule &
}

stop()
{
	echo "Stopping $SERVICE_NAME ............"
	kill `pgrep -f ${SERVICE_NAME}`
}

status()
{
	echo "$SERVICE_NAME status ............"
	ps -ef|grep ${SERVICE_NAME}|grep -v grep
}

case "$1" in
	start)
		start
		;;
	stop)
		stop
		;;
	status)
		status
		;;
	restart)
		stop
		echo restarting ..........
		sleep 5
		start
		;;

esac

echo "- $1  ...................................!!!"
