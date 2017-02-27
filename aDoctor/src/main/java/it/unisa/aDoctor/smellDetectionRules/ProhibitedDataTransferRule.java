package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class ProhibitedDataTransferRule {

    public boolean isProhibitedDataTransferRule(File androidManifest, ClassBean pClassBean) throws IOException {
        Pattern regex = Pattern.compile("(.*)android:name(\\s*)=(\\s*)\"android.permission.Internet\"", Pattern.MULTILINE);
        Pattern regex2 = Pattern.compile("(.*)android:name(\\s*)=(\\s*)\"android.permission.ACCESS_NETWORK_STATE\"", Pattern.MULTILINE);

        LineIterator iter = FileUtils.lineIterator(androidManifest);
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            Matcher regexMatcher2 = regex2.matcher(row);
            if (regexMatcher.find() || regexMatcher2.find()) {
                if (pClassBean.getTextContent().contains("GCMRegistrar")
                        || pClassBean.getTextContent().contains("NotificationManager")) {
                    if (!pClassBean.getTextContent().contains("getBackgroundDataSetting")){
                        return true;
                    }
                    
                
                }
            }
        }
        return false;
    }

}
