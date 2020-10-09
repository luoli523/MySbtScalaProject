#!/usr/bin/env bash

home="${HOME}"

if [ -z ${SPARK_HOME} ]; then
  echo "Shoud set the \$SPARK_HOME"
  exit 1
fi

submit_script="${SPARK_HOME}/bin/spark-submit"

this="${BASH_SOURCE-$0}"
submit_dir=$(cd -P -- "$(dirname -- "$this")" && pwd -P)
#submit_dir="${HOME}/dev/git/luoli/MySbtScalaProject"
submit_jar="spark-luoli_2.12-1.0.jar"

clz=$1
shift

bash ${submit_script} \
            --master local[*] \
            --class ${clz} \
            ${submit_dir}/target/scala-2.12/${submit_jar} \
            ${spark_home} \
            $@

