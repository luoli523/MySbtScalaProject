#!/usr/bin/env bash

home="${HOME}"
spark_home="${HOME}/soft/spark-2.4.0-bin-hadoop2.7"
submit_script="$spark_home/bin/spark-submit"
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

