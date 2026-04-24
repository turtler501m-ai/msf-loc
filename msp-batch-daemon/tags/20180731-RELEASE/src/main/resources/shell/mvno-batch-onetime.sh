#! /bin/bash

help()
{
	echo
	echo  "usage : mvno-batch-onetime.sh [start,stop,restart,status] [serverType : local|dev|live] [load package : bnd,cls,lgs,org,ptnr] [run scheduler : BondSchedule, LgsInvtrDaySumSchedule, .....]"
	echo  "ex] mvno-batch-onetime.sh start live bnd BondSchedule"
	echo
	
	exit 1;
}

if [ "$1" == "" ] || [ "$2" == "" ] || [ "$3" == "" ] || [ "$4" == "" ]; then
                help
fi

if [ "$1" != "start" ] && [ "$1" != "stop" ] && [ "$1" != "restart" ] && [ "$1" != "status" ]; then
                help
fi

if [ "$2" != "local" ] && [ "$2" != "dev" ] && [ "$2" != "live" ]; then
                help
fi

SERVICE_NAME=MvnoBatch_Onetime_$4

SERVER_TYPE=$2
if [ -z ${SERVER_TYPE} ]; then
	SERVER_TYPE=local
fi

LOAD_PACKAGE=$3
RUN_SCHEDULE=$4

echo "- SERVICE NAME : $SERVICE_NAME"
echo "- SERVER TYPE : $SERVER_TYPE"
echo "- LOAD PACKAGE : $LOAD_PACKAGE"
echo "- RUN SCHEDULE : $RUN_SCHEDULE"

# Set JAVA_HOME
#JAVA_HOME=$JAVA_HOME
echo JAVA_HOME: $JAVA_HOME


start()
{
	echo "Starting $SERVICE_NAME ............"
	$JAVA_HOME/bin/java -D${SERVICE_NAME} -jar ktmmBatch.jar ${SERVER_TYPE} onetime ${LOAD_PACKAGE} ${RUN_SCHEDULE} &
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
