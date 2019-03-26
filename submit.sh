#!/usr/bin/env bash

home="${HOME}"
spark_home="${SPARK_HOME}"
submit_script="${spark_home}/bin/spark-submit"

#this="${BASH_SOURCE-$0}"
#common_bin=$(cd -P -- "$(dirname -- "$this")" && pwd -P)
#script="$(basename -- "$this")"
#this="$common_bin/$script"

submit_dir="${HOME}/dev/git/luoli/MySbtScalaProject"
submit_jar="simple-spark_2.11-1.0.jar"

clz=$1
shift

bash ${submit_script} \
            --master local[*] \
            --class ${clz} \
            ${submit_dir}/target/scala-2.11/${submit_jar} \
            ${spark_home} \
            $@

