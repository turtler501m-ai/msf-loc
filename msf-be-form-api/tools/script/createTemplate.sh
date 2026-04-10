#!/usr/bin/env sh
set -eu

if [ "$#" -lt 2 ]; then
  echo "Usage: sh tools/script/createTemplate.sh <hexagonal|legacy> <baseDir>"
  echo "Usage: sh tools/script/createTemplate.sh module <baseDir> <moduleName>"
  echo "Example: sh tools/script/createTemplate.sh module domains _sample"
  exit 1
fi

TYPE="$1"
BASE_DIR="$2"
MODULE_NAME="${3:-}"

case "$TYPE" in
  hexagonal)
    TASK="createHexagonalPackages"
    EXPECTED_ARGS=2
    ;;
  legacy)
    TASK="createLegacyPackages"
    EXPECTED_ARGS=2
    ;;
  module)
    TASK="createGradleModule"
    EXPECTED_ARGS=3
    ;;
  *)
    echo "Unknown type: $TYPE"
    echo "Allowed types: hexagonal, legacy, module"
    exit 1
    ;;
esac

if [ "$#" -ne "$EXPECTED_ARGS" ]; then
  echo "Usage: sh tools/script/createTemplate.sh <hexagonal|legacy> <baseDir>"
  echo "Usage: sh tools/script/createTemplate.sh module <baseDir> <moduleName>"
  echo "Example: sh tools/script/createTemplate.sh module domains _sample"
  exit 1
fi

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
PROJECT_ROOT=$(CDPATH= cd -- "$SCRIPT_DIR/../.." && pwd)

cd "$PROJECT_ROOT"
if [ "$TYPE" = "module" ]; then
  ./gradlew "$TASK" "-PbaseDir=$BASE_DIR" "-PmoduleName=$MODULE_NAME"
else
  ./gradlew "$TASK" "-PbaseDir=$BASE_DIR"
fi
