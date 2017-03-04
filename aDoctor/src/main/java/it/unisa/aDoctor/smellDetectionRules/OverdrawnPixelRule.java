package it.unisa.aDoctor.smellDetectionRules;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class OverdrawnPixelRule {

    public boolean isOverdrawnPixelRule(File layoutFile) throws IOException {
        Pattern regex = Pattern.compile("color|Color", Pattern.MULTILINE);

        LineIterator iter = FileUtils.lineIterator(layoutFile);

        int count = 0;
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);

            if (regexMatcher.find()) {
                count++;

                if (count >= 10) {
                    return true;
                }

            }
        }
        return false;
    }

}
