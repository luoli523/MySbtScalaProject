package luoli523.spark.dataframe

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions.{col, expr, concat}

object Schema {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .appName("Spark DataFrame: Schema Example")
      .getOrCreate()

    if (args.length <= 0) {
      println("usage Schema <file path to blogs.json")
      System.exit(1)
    }

    //get the path to the JSON file
    val jsonFile = args(0)
    //define our schema as before
    val schema = StructType(
      Array(
        StructField("Id", IntegerType, false),
        StructField("First", StringType, false),
        StructField("Last", StringType, false),
        StructField("Url", StringType, false),
        StructField("Published", StringType, false),
        StructField("Hits", IntegerType, false),
        StructField("Campaigns", ArrayType(StringType), false)
      )
    )

    // Create a DataFrame by reading from the JSON file a predefined Schema
    // {"Id":1, "First": "Jules", "Last":"Damji", "Url":"https://tinyurl.1", "Published":"1/4/2016", "Hits": 4535, "Campaigns": ["twitter", "LinkedIn"]}
    // {"Id":2, "First": "Brooke","Last": "Wenig","Url": "https://tinyurl.2", "Published": "5/5/2018", "Hits":8908, "Campaigns": ["twitter", "LinkedIn"]}
    // {"Id": 3, "First": "Denny", "Last": "Lee", "Url": "https://tinyurl.3","Published": "6/7/2019","Hits": 7659, "Campaigns": ["web", "twitter", "FB", "LinkedIn"]}
    // {"Id": 4, "First":"Tathagata", "Last": "Das","Url": "https://tinyurl.4", "Published": "5/12/2018", "Hits": 10568, "Campaigns": ["twitter", "FB"]}
    // {"Id": 5, "First": "Matei","Last": "Zaharia","Url": "https://tinyurl.5", "Published": "5/14/2014", "Hits": 40578, "Campaigns": ["web", "twitter", "FB", "LinkedIn"]}
    // {"Id":6,  "First": "Reynold", "Last": "Xin", "Url": "https://tinyurl.6", "Published": "3/2/2015", "Hits": 25568, "Campaigns": ["twitter", "LinkedIn"] }
    val blogsDF = spark.read.schema(schema).json(jsonFile)
    //show the DataFrame schema as output
    blogsDF.show(truncate = false)
    // print the schemas
    print(blogsDF.printSchema)
    print(blogsDF.schema)
    // Show columns and expressions
    blogsDF.select(expr("Hits") * 2).show(2)
    blogsDF.select(col("Hits") * 2).show(2)
    blogsDF.select(expr("Hits * 2")).show(2)
    // show heavy hitters
    blogsDF.withColumn("Big Hitters", (expr("Hits > 10000"))).show()

    // Concatenate three columns, create a new column, and show the // newly created concatenated column
    blogsDF
      .withColumn("AuthorsId", (concat(expr("First"), expr("Last"), expr("Id")))) .select(col("AuthorsId"))
      .show(4)


    // These statements return the same value, showing that
    // expr is the same as a col method call
    blogsDF.select(expr("Hits")).show(2)
    blogsDF.select(col("Hits")).show(2)
    blogsDF.select("Hits").show(2)

    // Sort by column "Id" in descending order
    blogsDF.sort(col("Id").desc).show()
    //blogsDF.sort($"Id".desc).show()

  }
}