package Logger;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    protected int threshold;

    public Filter(int threshold) {
        this.threshold = threshold;
    }

    public List<Integer> filterOut(List<Integer> source) {
        Logger logger = Logger.getInstance();
        List<Integer> result = new ArrayList<>();

        for (int num : source) {
            if (num >= threshold) {
                result.add(num);
                logger.log("Элемент \"" + num + "\" проходит");
            } else {
                logger.log("Элемент \"" + num + "\" не проходит");
            }
        }

        logger.log("Прошло фильтр " + result.size() + " элемента из " + source.size());
        return result;
    }
}
