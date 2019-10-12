package DocCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DocCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    IntWritable value = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // 1. 统计文档总个数
        int sum = 0;
        for (IntWritable count : values)
        {
            sum += count.get();
        }

        // 2. 输出单词总个数
        value.set(sum);
        context.write(key, value);
    }
}
