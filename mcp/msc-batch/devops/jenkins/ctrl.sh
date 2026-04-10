#!/bin/bash

#---------------------------------------------------------------------
#-- Set Error if Variable is Not Set
#-- Set Exit if Error is Occured
#---------------------------------------------------------------------

#set -o nounset
#set -o errexit

source /home/jboss/.bash_profile

#---------------------------------------------------------------------
# // Check Parameter & Define Variable
#---------------------------------------------------------------------

if [[ $# -lt 1 ]]; then
    if [[ $# != $1 ]]; then
        echo
        echo "[ ERROR ] NOT FOUND CMD : ($1)"
        echo "[ INFO ] ex ) ./ctrl.sh ("start" or "stop" or "restart" or "status" or "backup" or "log")"
        echo
        exit 1
    fi
fi
VALUE=$1
ST_LIST=("start" "stop" "restart" "status" "backup" "log")

COLLECT_ENV=`hostname`
export ENV_SET=`echo ${COLLECT_ENV##*-}|tr [:upper:] [:lower:]`

export PORT_SET=1000
export HTTP_PORT=$(expr 8080 + $PORT_SET)
export INST_DIR=$(cd  "$(dirname "$0")" && pwd)
export APP_NAME=`basename ${INST_DIR}`
export WAS_DIR=${BASE_DIR}/domains/${APP_NAME}/deployments
export LOG_DIR=${BASE_DIR}/jb_log/${APP_NAME}
export LOG_NAME=server.log
export BACKUP_DIR=${INST_DIR}/.backup
export JAR_FILE=${WAS_DIR}/${APP_NAME}.war
export JAVA_HOME=${BASE_DIR}/appsw/jdk-11.0.12
export YEAR=`date '+%Y'`
export DATE=`date '+%Y%m%d%H%M%S'`
export WAIT_TIME=20;

if [ "x$JAVA_OPTS" = "x" ]; then
    # The hotspot server JVM has specific code-path optimizations 
    # which yield an approximate 10% gain over the client version. 
    JAVA_OPTS="-server" 

    # user custmized setting
#    JAVA_OPTS="$JAVA_OPTS -d64" 
    JAVA_OPTS="$JAVA_OPTS -XX:+UnlockDiagnosticVMOptions" 
#    JAVA_OPTS="$JAVA_OPTS -XX:+G1SummarizeConcMark" 
    JAVA_OPTS="$JAVA_OPTS -XX:InitiatingHeapOccupancyPercent=35" 
    JAVA_OPTS="$JAVA_OPTS -XX:ParallelGCThreads=8" 
    JAVA_OPTS="$JAVA_OPTS -XX:ConcGCThreads=8" 
    JAVA_OPTS="$JAVA_OPTS -XX:+UseLargePagesInMetaspace" 
    JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=${ENV_SET}"
    JAVA_OPTS="$JAVA_OPTS -Dserver.port=${HTTP_PORT}"
    JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8"

    # discourage address map swapping by setting Xms and Xmx to the same value 
    # http://confluence.atlassian.com/display/DOC/Garbage+Collector+Performance+Issues 
    JAVA_OPTS="$JAVA_OPTS -Xms2048m -Xmx2048m" 

    # Increase maximum perm size for web base applications to 4x the default amount 
    # http://wiki.apache.org/tomcat/FAQ/Memoryhttp://wiki.apache.org/tomcat/FAQ/Memory 
    # jdk7
    #export JAVA_OPTS="$JAVA_OPTS -XX:PermSize=256m" 
    #export JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize=512m" 
    # jdk8
    JAVA_OPTS="$JAVA_OPTS -XX:MetaspaceSize=512m" 
    JAVA_OPTS="$JAVA_OPTS -XX:MaxMetaspaceSize=1024m" 

    #JAVA_OPTS="$JAVA_OPTS -Xss192k" 

    # Oracle Java as default, uses the serial garbage collector on the 
    # Full Tenured heap. The Young space is collected in parallel, but the 
    # Tenured is not. This means that at a time of load if a full collection 
    # event occurs, since the event is a 'stop-the-world' serial event then 
    # all application threads other than the garbage collector thread are 
    # taken off the CPU. This can have severe consequences if requests continue 
    # to accrue during these 'outage' periods. (specifically webservices, webapps) 
    # [Also enables adaptive sizing automatically] 
    JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC" 
 
    # This is interpreted as a hint to the garbage collector that pause times 
    # of <nnn> milliseconds or less are desired. The garbage collector will 
    # adjust the  Java heap size and other garbage collection related parameters 
    # in an attempt to keep garbage collection pauses shorter than <nnn> milliseconds. 
    # http://java.sun.com/docs/hotspot/gc5.0/ergo5.html 
    JAVA_OPTS="$JAVA_OPTS -XX:MaxGCPauseMillis=1500" 

    # Verbose GC 
#    JAVA_OPTS="$JAVA_OPTS -verbose:gc" 
#    JAVA_OPTS="$JAVA_OPTS -Xloggc:$LOG_DIR/gclog/gc.log.$DATE" 
#    JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails" 
#    JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps" 
#    JAVA_OPTS="$JAVA_OPTS -XX:+PrintHeapAtGC" 
    JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError" 
    JAVA_OPTS="$JAVA_OPTS -XX:HeapDumpPath=$LOG_DIR/gclog/${APP_NAME}.$DATE.hprof" 
    JAVA_OPTS="$JAVA_OPTS -XX:+ExplicitGCInvokesConcurrent"
#    JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime" 
    JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.client.gcInterval=36000000" 
    JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.server.gcInterval=36000000" 
    JAVA_OPTS="$JAVA_OPTS -Dsun.lang.ClassLoader.allowArraySyntax=true" 

    # Verbose GC(jdk11) 
    JAVA_OPTS="$JAVA_OPTS -verbose:gc"
#    JAVA_OPTS="$JAVA_OPTS -Xlog:gc*,safepoint:$LOG_DIR/gclog/gc.log.$DATE:time,uptime,level,tags:filecount=5,filesize=128K"
    JAVA_OPTS="$JAVA_OPTS -Xlog:gc*=info,gc+heap=trace:file=$LOG_DIR/gclog/gc.log.$DATE:time"
 
    # Disable remote (distributed) garbage collection by Java clients 
    # and remove ability for applications to call explicit GC collection 
    JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC" 
 
    # Prefer IPv4 over IPv6 stack 
    JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true" 
 
    # Set Java Server TimeZone to UTC 
    JAVA_OPTS="$JAVA_OPTS -Duser.timezone=GMT+9" 

    export JAVA_OPTS
fi

#---------------------------------------------------------------------
# // TOMCAT TASK START
#---------------------------------------------------------------------
func_main() {
    func_ctrl ${VALUE} ST_LIST ${APP_NAME} ${JAVA_HOME} JAVA_OPTS ${JAR_FILE} ${BACKUP_DIR} ${YEAR} ${DATE} ${WAIT_TIME} ${LOG_DIR}/${LOG_NAME}
}

# JAR_FILE
func_pid() {
    local VAL1=$1
    PID=`ps fax|grep java|grep "${VAL1}"|grep ".war"|awk '{print $1}'`
    echo ${PID}
}

func_ctrl() {
    local VAL1=$1
    local VAL2=$2
    local VAL3=$3
    local VAL4=$4
    local VAL5=$5
    local VAL6=$6
    local VAL7=$7
    local VAL8=$8
    local VAL9=${9}
    local VAL10=${10}
    local VAL11=${11}
    
    CMD=$(func_cmd_chk ${VAL1} ${VAL2})
    if [[ -z ${CMD} ]]; then
        echo
        echo "[ ERROR ] PLEASE CHECK YOUR VALUE_1 : ${VAL1}"
        echo "[ INFO ] ex ) ./ctrl.sh ("start" or "stop" or "restart" or "status" or "backup" or "log")"
        echo
        exit 1
    fi

    case ${CMD} in
        "start")
            func_start ${VAL3} ${VAL4} ${VAL5} ${VAL6} ${VAL11} ${VAL9}
            ;;
        "stop")
            func_stop ${VAL3} ${VAL10}
            ;;
        "restart")
            func_stop ${VAL3} ${VAL10}
            sleep 1
            func_start ${VAL3} ${VAL4} ${VAL5} ${VAL6} ${VAL11} ${VAL9}
            ;;
        "status")
            func_proc_chk ${VAL3}
            ;;
        "backup")
            func_backup ${VAL6} ${VAL7} ${VAL8} ${VAL3} ${VAL9}
            ;;
        "log")
            func_log ${VAL11}
            ;;
    esac
}

func_cmd_chk() {
    local VAL1=$1
    local VAL2=$2[@]
    local VAL2=("${!VAL2}")

    for VAL3 in ${VAL2[@]}; do
        if [[ ${VAL1} == ${VAL3} ]]; then
            echo "${VAL1}"
        else
            continue
        fi
    done
}

func_proc_chk() {
    local VAL1=$1
    echo
    echo "[ INFO ] func_proc_chk"
    local status=$(func_pid ${VAL1});
    if [[ ! -z "${status}" ]]; then
        echo "[ INFO ] ${VAL1} service is running. >> ( ${status} )"
    else
        echo "[ WARN ] ${VAL1} service is not running"
    fi
}

func_dir_chk() {
    local VAL1=$1
    echo
    echo "[ INFO ] func_dir_chk"
    if [[ ! -d ${VAL1} ]]; then
        echo "[ INFO ] mkdir -p ${VAL1}"
        mkdir -p ${VAL1}
    fi
}

func_start() {
    local VAL1=$1
    local VAL2=$2
    local VAL3=$3[@]
    local VAL3=("${!VAL3}")
    local VAL4=$4
    local VAL5=$5
    local VAL6=$6
    echo
    echo "[ INFO ] func_start"
    local status=$(func_pid ${VAL1});
    if [[ ! -z "${status}" ]]; then {
        echo "[ INFO ] ${VAL1} service is still running. >> ( ${status} )"
    }
    else {
        echo "[ INFO ] ${VAL1} service is start.";
        #func_move ${VAL5} ${VAL6}
        #echo "[ INFO ] nohup ${VAL2}/bin/java -jar ${VAL3} ${VAL4} >> ${VAL5} 2>&1 &"
        #nohup ${VAL2}/bin/java -jar ${VAL3} ${VAL4} >> ${VAL5} 2>&1 &
        echo "[ INFO ] nohup ${VAL2}/bin/java -jar ${VAL3} ${VAL4} > /dev/null 2>&1 &"
        nohup ${VAL2}/bin/java -jar ${VAL3} ${VAL4} > /dev/null 2>&1 &
    } fi;
    return 0;
}

func_stop() {
    local VAL1=$1
    local VAL2=$2
    echo
    echo "[ INFO ] func_stop"
    local status=$(func_pid ${VAL1});
    if [[ ! -z "${status}" ]]; then {
        kill -TERM ${status}
        echo -ne "[ INFO ] ${VAL1} service is stop.";
        kwait=${VAL2};
        count=0;
        while kill -0 ${status} 2>/dev/null && [ ${count} -le ${kwait} ]; do {
            printf ".";
            sleep 1;
            (( count++ ));
        } done;
        echo;
        if [[ ${count} -gt ${kwait} ]]; then {
            printf "[ INFO ] Process is Still Running then, %d seconds later Forced Terminate This Process." \
            ${VAL2};
            kill ${status};
            sleep 3;
            # if it's still running use kill -9
            #
            if kill -0 ${status} 2>/dev/null; then {
                echo "[ INFO ] Process is Still Running, Act kill -9";
                kill -9 ${status}
                sleep 3;
            } fi;
        } fi;
    } 
    else {
        echo "[ INFO ] ${VAL1} service is not running";
    } fi;
    #return 0;
}

func_backup() {
    local VAL1=$1
    local VAL2=$2
    local VAL3=$3
    local VAL4=$4
    local VAL5=$5
    func_dir_chk ${VAL2}/${VAL3}
    echo
    echo "[ INFO ] func_backup"
    echo "[ INFO ] cp ${VAL1} ${VAL2}/${VAL3}/${VAL4}.jar.${VAL5}"
    cp ${VAL1} ${VAL2}/${VAL3}/${VAL4}.jar.${VAL5}
    func_remove ${VAL2}/${VAL3}
}

func_remove() {
    local VAL1=$1
    echo
    echo "[ INFO ] func_remove"
    echo "[ INFO ] cd ${VAL1}; find ./* -mtime +1 | xargs -n1 rm -f"
    cd ${VAL1}; find ./* -mtime +1 | xargs -n1 rm -f
}

func_move() {
    local VAL1=$1
    local VAL2=$2
    if [[ -f ${VAL1} ]]; then
        echo "[ INFO ] mv ${VAL1} ${VAL1}.${VAL2}"
        mv ${VAL1} ${VAL1}.${VAL2}
    fi
}

func_log() {
    local VAL1=$1
    echo "[ INFO ] tail -100f ${VAL1}"
    tail -100f ${VAL1}
}

func_main
