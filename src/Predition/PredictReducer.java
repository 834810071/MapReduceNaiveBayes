package Predition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


import java.io.IOException;

// 输入为 <docId,list<ClassName,Prob>>，找到最大的 Prob，输出 <docId,最大Prob对应的ClassName>
public class PredictReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Text value = new Text();
        String max_classname = "";
        double max_prob = Double.NEGATIVE_INFINITY;
        for (Text text : values)
        {
            String[] args = text.toString().split("&");
            if (Double.valueOf(args[1]) > max_prob)
            {
                max_classname = args[0];
                max_prob = Double.valueOf(args[1]);
            }
        }
        value.set(max_classname);
        context.write(key, value);
    }
}
