package WordCount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/*
 * 输入的key LongWritable   行号
 * 输入的value Text         一行内容
 * 输出的key Text           单词
 * 输入的value IntWritable  单词的个数
 */
public class WordCountMapper extends Mapper<LongWritable, Text, TextPair, IntWritable> {

    Text className = new Text();
    Text wordName = new Text();
    TextPair k = new TextPair();
    IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1. 获取类名
        InputSplit inputSplit = context.getInputSplit();
        String dirName = ((FileSplit) inputSplit).getPath().getParent().getName();

        // 2. 行内容转换为string
        String line = value.toString();

        // 3. 切割  按单词
        String[] words = line.split(" ");

        // 4. 循环写出当下的一个截断
        for (String word : words)
        {
            className.set(dirName);
            wordName.set(word);
            k.set(className, wordName);
            context.write(k, v);
        }

    }
}
