package WordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<TextPair, IntWritable, TextPair, IntWritable> {

    @Override
    protected void reduce(TextPair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // 1. 统计单词总个数
        int sum = 0;
        for (IntWritable count : values)
        {
            sum += count.get();
        }

        // 2. 输出单词总个数
        context.write(key, new IntWritable(sum));
    }
}
