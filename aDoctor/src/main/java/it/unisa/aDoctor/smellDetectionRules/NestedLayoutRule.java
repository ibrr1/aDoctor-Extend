package it.unisa.aDoctor.smellDetectionRules;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class NestedLayoutRule {

    public boolean isNestedLayoutRule(File layoutFile) throws IOException {
        Pattern regex = Pattern.compile("<LinearLayout", Pattern.MULTILINE);
        Pattern regex2 = Pattern.compile("(.*)android:layout_width(\\s*)=(\\s*)", Pattern.MULTILINE);

        LineIterator iter = FileUtils.lineIterator(layoutFile);

        int count = 0;
        int count2 = 0;
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            Matcher regexMatcher2 = regex2.matcher(row);

            if (regexMatcher.find()) {
                count++;      
            }
            
            if (regexMatcher2.find()) {
                count2++;
            }
            
            if (count >=2 && count2 >=2){
                return true;
            }
        }
        return false;
    }

}
