package it.unisa.aDoctor.smellDetectionRules;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class UnnecessaryPermissionRule {

    public boolean isUnnecessaryPermission(File androidManifest) throws IOException {
        Pattern regex = Pattern.compile("(.*)android:name(\\s*)=(\\s*)\"android.permission.CAMERA\"", Pattern.MULTILINE);
        Pattern regex2 = Pattern.compile("(.*)android:name(\\s*)=(\\s*)\"android.permission.SEND_SMS\"", Pattern.MULTILINE);
        Pattern regex3 = Pattern.compile("(.*)android:name(\\s*)=(\\s*)\"android.permission.CALL_PHONE\"", Pattern.MULTILINE);
        Pattern regex4 = Pattern.compile("(.*)android:name(\\s*)=(\\s*)\"android.permission.READ_CONTACTS\"", Pattern.MULTILINE);

        LineIterator iter = FileUtils.lineIterator(androidManifest);
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            Matcher regexMatcher2 = regex2.matcher(row);
            Matcher regexMatcher3 = regex3.matcher(row);
            Matcher regexMatcher4 = regex4.matcher(row);
            if (regexMatcher.find() || regexMatcher2.find() || regexMatcher3.find() || regexMatcher4.find() ) {
                return true;
            }
        }
        return false;
    }

}
