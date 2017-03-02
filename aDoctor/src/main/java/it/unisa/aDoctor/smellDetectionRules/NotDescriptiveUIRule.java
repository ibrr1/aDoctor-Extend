package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class NotDescriptiveUIRule {

    int count = 0;

    public boolean isNotDescriptiveUIRule(File layoutFile) throws IOException {
        Pattern regex = Pattern.compile("android:contentDescription", Pattern.MULTILINE);
       
        LineIterator iter = FileUtils.lineIterator(layoutFile);

        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            

            if (regexMatcher.find()) {
                return false;
            }

        }
        return true;
    }

}
