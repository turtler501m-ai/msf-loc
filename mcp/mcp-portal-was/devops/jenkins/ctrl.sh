#!/bin/bash

#--------------------------------------------------------------------------
#-- Set Error if Variable is Not Set
#-- Set Exit if Error is Occured
#--------------------------------------------------------------------------
#set -o nounset
#set -o errexit

source /home/jboss/.bash_profile
#--------------------------------------------------------------------------
# // Check Parameter & Define Variable
#--------------------------------------------------------------------------
# .bash_profile in param(instNm)
INST_NAME=${instNm}

ID="jboss"
USER=`id -u -n`
CHECK_LIST=("NULL" "UID" "APPNM" "PATH" "PID" "DIR" "MV")

WAS_DIR=/jboss/domains
INST_NAME_LIST=("mcpadmin" "mcpapi" "mcpprx" "mcpwas" "mcpwaswidget")
ST_LIST=("0" "1")
ENV_FILE=env.sh
APP_NAME="ctrl.sh"
DATE=`date +%Y%m%d%H%M%S`

#---------------------------------------------------------------
# // Validate Parameter & Run Script
#---------------------------------------------------------------
func_init() {
    func_check CHECK_LIST ${CHECK_LIST[0]} "${INST_NAME}" "" ""
    func_check CHECK_LIST ${CHECK_LIST[1]} ${USER} ${ID} ""
    func_check CHECK_LIST ${CHECK_LIST[2]} INST_NAME_LIST "${INST_NAME}" ST_LIST
    func_main CHECK_LIST "${CHECK_LIST[3]}|${CHECK_LIST[4]}|${CHECK_LIST[5]}|${CHECK_LIST[6]}" ${WAS_DIR} ${INST_NAME} ${ENV_FILE} ${APP_NAME} ${DATE}
}

func_check() {
    local VAL1=$1[@]
    local VAL1=("${!VAL1}")
    local VAL2=$2
    local VAL3=$3
    local VAL4=$4
    local VAL5=$5[@]
    local VAL5=("${!VAL5}")

    if [[  ${VAL2} == "${VAL1[0]}" ]]; then
        if [[ -z ${VAL3} ]]; then
            echo "[ ERROR ] check ${VAL2} parms : ( ${VAL3} )"
            exit 1;
        fi
    elif [[  ${VAL2} == "${VAL1[1]}" ]]; then
        if [[ ${VAL4} != "${VAL3}" ]]; then
            echo "[ ERROR ] user access denied."
            exit 1;
        fi
    elif [[  ${VAL2} == "${VAL1[2]}" ]]; then
        local TMP=${VAL3}[@]
        local TMP=("${!TMP}")
        local ST=${VAL5[1]}
        for VAL in ${TMP[@]}; do
            if [[ ${VAL4} == ${VAL} ]]; then
                ST=${VAL5[0]}
            fi
        done
        if [[ ${ST} -ne 0 ]]; then
            echo "[ ERROR ] check ${VAL2} parms : ( ${VAL4} )"
            exit 1;
        fi
    elif [[  ${VAL2} == "${VAL1[3]}" ]]; then
        local CNT=`find ${VAL3} -maxdepth 1 -type d -name "${VAL4}*"|wc -l`
        if [[ ${CNT} -lt 1 ]]; then
            echo "[ ERROR ] check ${VAL2} parms : ( ${VAL4} )"
            exit 1;
        fi
    elif [[  ${VAL2} == "${VAL1[4]}" ]]; then
        local PID=`ps -ef | grep java | grep -v "${VAL3}" | grep "${VAL4}" | awk '{print $2}'`
        echo "${PID}"
    elif [[  ${VAL2} == "${VAL1[5]}" ]]; then
        if [[ ! -d ${VAL3} ]]; then
            echo "[ INFO ] mkdir -p ${VAL3}"
            mkdir -p ${VAL3}
        fi
    elif [[  ${VAL2} == "${VAL1[6]}" ]]; then
        if [[ -f ${VAL3} ]]; then
            echo "[ INFO ] mv ${VAL3} ${VAL3}.${VAL4}"
            mv ${VAL3} ${VAL3}.${VAL4}
        fi
    fi
}

func_main() {
    local VAL1=$1
    local VAL2=$2
    IFS='|' read -a VAL2 <<< "${VAL2}"
    local VAL3=$3
    local VAL4=$4
    local VAL5=$5
    local VAL6=$6
    local VAL7=$7

    func_check ${VAL1} ${VAL2[0]} "${VAL3}" "${VAL4}" ""
    local INST_LIST=`find ${VAL3}/${VAL4}* -name "${VAL5}"`
    for VAL in ${INST_LIST[@]}; do
        func_process_mng ${VAL1} "${VAL2[1]}|${VAL2[2]}|${VAL2[3]}" ${VAL} ${VAL6} ${VAL7}
        if [[ `hostname` =~ "PRD" ]]; then
            sleep 50;
        fi
    done
}

func_process_mng() {
    local VAL1=$1
    local VAL2=$2
    IFS='|' read -a VAL2 <<< "${VAL2}"
    local VAL3=$3
    local VAL4=$4
    local VAL5=$5

    func_stop ${VAL1} ${VAL2[0]} ${VAL3} ${VAL4}
    func_start ${VAL1} "${VAL2[0]}|${VAL2[1]}|${VAL2[2]}" ${VAL3} ${VAL4} ${VAL5}
}

func_stop() {
    local VAL1=$1
    local VAL2=$2
    local VAL3=$3
    local VAL4=$4
    local INST_BIN=`dirname ${VAL3}`
    local ENV_FILE=`basename ${VAL3}`
    unset JAVA_OPTS
    cd ${INST_BIN}; . ./${ENV_FILE};
    local status=$(func_check ${VAL1} ${VAL2} "${VAL4}" "=$SERVER_NAME " "")
    if [[ ! -z ${status} ]]; then
        echo "[ INFO ] $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=$CONTROLLER_IP:$CONTROLLER_PORT --command=:shutdown"
        $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=$CONTROLLER_IP:$CONTROLLER_PORT --command=:shutdown
        sleep 3
    fi
    local status=$(func_check ${VAL1} ${VAL2} "${VAL4}" "=$SERVER_NAME " "")
    if [[ -z ${status} ]]; then
        echo "[ INFO ] JBoss SERVER - $SERVER_NAME is Not RUNNING..."
    else
        echo "[ WARN ] JBoss SERVER - $SERVER_NAME is Still RUNNING..."
        echo "[ WARN ] JBoss SERVER - $SERVER_NAME  forced kill >> ( ${status} )"
        kill -9 ${status};
    fi
}

func_start() {
    local VAL1=$1
    local VAL2=$2
    IFS='|' read -a VAL2 <<< "${VAL2}"
    local VAL3=$3
    local VAL4=$4
    local VAL5=$5
    local INST_BIN=`dirname ${VAL3}`
    local ENV_FILE=`basename ${VAL3}`
    unset JAVA_OPTS
    cd ${INST_BIN}; . ./${ENV_FILE};
    local status=$(func_check ${VAL1} ${VAL2[0]} "${VAL4}" "=$SERVER_NAME " "")
    if [[ -z ${status} ]]; then
        echo "[ INFO ] >>> $JAVA_OPTS"
        func_check ${VAL1} ${VAL2[1]} "$LOG_HOME/nohup" "" ""
        func_check ${VAL1} ${VAL2[1]} "$LOG_HOME/gclog" "" ""
        local FILENM="$LOG_HOME/nohup/$SERVER_NAME.out"
        func_check CHECK_LIST ${VAL2[2]} "${FILENM}" "${VAL5}" ""
        local PROPFILE="$DOMAIN_BASE/$SERVER_NAME/bin/env.properties"

        echo "[ INFO ] nohup $JBOSS_HOME/bin/standalone.sh -DSERVER=$SERVER_NAME -P=${PROPFILE} -c $CONFIG_FILE >> ${FILENM}  2>&1 &"
        nohup $JBOSS_HOME/bin/standalone.sh -DSERVER=$SERVER_NAME -P=${PROPFILE} -c $CONFIG_FILE >> ${FILENM}  2>&1 &
        sleep 3
    else
        echo "[ INFO ] JBoss SERVER - $SERVER_NAME is already RUNNING..."
    fi

    local status=$(func_check ${VAL1} ${VAL2[0]} "${VAL4}" "=$SERVER_NAME " "")
    if [[ ! -z ${status} ]]; then
        echo "[ INFO ] Starting... $SERVER_NAME"
    else
        echo "[ ERROR ] JBoss SERVER - $SERVER_NAME is Not RUNNING..."
        echo "[ ERROR ] Please Check Process.."
        exit 1;
    fi
}

func_init