package it.unisa.aDoctor.smellDetectionRules;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class UncontrolledFocusOrderRule {

    public boolean isUncontrolledFocusOrderRule(File androidManifest) throws IOException {
        Pattern regex = Pattern.compile("(.*)android:layout_width(\\s*)=(\\s*)\"fill_parennnt\"", Pattern.MULTILINE);
       
        LineIterator iter = FileUtils.lineIterator(androidManifest);
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

}
