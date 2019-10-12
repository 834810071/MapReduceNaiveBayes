package Predition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.File;
import java.io.IOException;

public class PredictTestRecordReader extends RecordReader<NullWritable, Text> {

    FileSplit split;
    Configuration conf;
    Text value = new Text();
    boolean isProcess = false;
    LineRecordReader reader = new LineRecordReader();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        // 初始化
        split = (FileSplit) split;
        conf = context.getConfiguration();
        reader.initialize(split, context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // 读取一个一个文件
        if (!isProcess)
        {
            String result = ""; // 单词 + "\n" + 单词 + "\n" ... + 单词 + "\n"
            while (reader.nextKeyValue())
            {
                result += reader.getCurrentValue() + "\n";
            }
            value.set(result);
            isProcess = true;
            return true;
        }
        return false;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {
        return NullWritable.get();
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return reader.getProgress();
    }

    @Override
    public void close() throws IOException {

    }
}
