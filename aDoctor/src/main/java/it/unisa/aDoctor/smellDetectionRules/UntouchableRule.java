package it.unisa.aDoctor.smellDetectionRules;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class UntouchableRule {

    public boolean isUntouchableRule(File androidManifest) throws IOException {
        Pattern regex = Pattern.compile("(.*)android:layout_width(\\s*)=(\\s*)\"\\d{1,}dp\"(\\s*)|(.*)android:layout_height(\\s*)=(\\s*)\"\\d{1,}dp\"(\\s*)", Pattern.MULTILINE);

        LineIterator iter = FileUtils.lineIterator(androidManifest);

        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            
            if (regexMatcher.find()) {
                String size = regexMatcher.group().trim();
                size = size.replaceAll("\\D+", "");
                int elementSize = Integer.parseInt(size);
                
                if (elementSize < 48){
                    return true;      
                }
            }
        }
        return false;
    }

}
