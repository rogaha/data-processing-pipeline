package org.sparkexample;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

public class WordCount {
  private static final FlatMapFunction<String, String> WORDS_EXTRACTOR =
      new FlatMapFunction<String, String>() {
        @Override
        public Iterable<String> call(String s) throws Exception {
          return Arrays.asList(s.split(" "));
        }
      };

  private static final PairFunction<String, String, Integer> WORDS_MAPPER =
      new PairFunction<String, String, Integer>() {
        @Override
        public Tuple2<String, Integer> call(String s) throws Exception {
          return new Tuple2<String, Integer>(s, 1);
        }
      };

  private static final Function2<Integer, Integer, Integer> WORDS_REDUCER =
      new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer call(Integer a, Integer b) throws Exception {
          return a + b;
        }
      };

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Please provide the input file full path as argument");
      System.exit(0);
    }

    SparkConf conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster("local");
    JavaSparkContext context = new JavaSparkContext(conf);

    JavaRDD<String> file = context.textFile(args[0]);
    JavaRDD<String> words = file.flatMap(WORDS_EXTRACTOR);
    JavaPairRDD<String, Integer> pairs = words.mapToPair(WORDS_MAPPER);
    JavaPairRDD<String, Integer> counter = pairs.reduceByKey(WORDS_REDUCER);

    counter.saveAsTextFile(args[1]);
  }
}
