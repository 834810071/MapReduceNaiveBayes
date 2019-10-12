package DocCount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class DocCountMapper extends Mapper<NullWritable, BytesWritable, Text, IntWritable> {
    Text k = new Text();
    IntWritable v = new IntWritable(1);

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取文件的路径和名称(类名)
        FileSplit split = (FileSplit) context.getInputSplit();

        Path path = split.getPath();    // 当前文件路径
        k.set(path.getParent().getName());  // 文件所在文件夹名称
    }

    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        context.write(k, v);
    }
}
