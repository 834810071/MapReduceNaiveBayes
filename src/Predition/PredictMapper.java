package Predition;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

// Map 的输入为 <Null, content>，Map 的输出为list<docId, <ClassName, Prob>>
public class PredictMapper extends Mapper<NullWritable, Text, Text, Text> {
    Text k = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取文件路径和名称(类名)
        FileSplit split = (FileSplit) context.getInputSplit();

        Path path = split.getPath();
        k.set(path.getName() + "&" + path.getParent().getName());   // 文件名称 & 文件夹名称(真实类别)
    }

    // value 单词 + "\n" + 单词 + "\n" ... + 单词 + "\n"
    @Override
    protected void map(NullWritable key, Text value, Context context) throws IOException, InterruptedException {
        Text result = new Text();
        String[] CLASS_NAMES = {"CHINA", "CANA"};
        for (String classname : CLASS_NAMES)
        {
            result.set(classname + "&" + Double.toString(Prediction.conditionalProbabilityForClass(value.toString(), classname)));  // 文件夹名称 + "&" + P(c|d)
            context.write(k, result);
        }
    }
}
