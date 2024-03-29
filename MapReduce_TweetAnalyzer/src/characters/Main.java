package characters;

import java.util.Arrays;

import characters.TweetMapper;
import characters.TweetReducer;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {

    public static void runJob(String[] input, String output) throws Exception {

        Configuration conf = new Configuration();

        Job job = new Job(conf);
        job.setJarByClass(Main.class);
        job.setMapperClass(TweetMapper.class);
        job.setReducerClass(TweetReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        Path outputPath = new Path(output);
        FileInputFormat.setInputPaths(job, StringUtils.join(input, ","));
        FileOutputFormat.setOutputPath(job, outputPath);
        outputPath.getFileSystem(conf).delete(outputPath,true);
        job.waitForCompletion(true);
        job.setNumReduceTasks(3);
    }

    public static void main(String[] args) throws Exception {
        runJob(Arrays.copyOfRange(args, 0, args.length-1), args[args.length-1]);
    }

}
