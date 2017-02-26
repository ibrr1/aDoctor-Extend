package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class TrackingHardwareIdRule {

    public boolean isTrackingHardwareIdRule(File androidManifest, ClassBean pClassBean) throws IOException {
        Pattern regex = Pattern.compile("(.*)android:name(\\s*)=(\\s*)\"android.permission.READ_PHONE_STATE\"", Pattern.MULTILINE);

        LineIterator iter = FileUtils.lineIterator(androidManifest);
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            if (regexMatcher.find()) {
                if (pClassBean.getTextContent().contains("getDeviceId")) {
                return true;
                }
            }
        }
        return false;
    }

}
