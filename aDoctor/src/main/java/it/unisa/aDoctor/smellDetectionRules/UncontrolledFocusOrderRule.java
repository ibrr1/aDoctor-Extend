package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class UncontrolledFocusOrderRule {

    int count = 0;

    public boolean isUncontrolledFocusOrderRule(File layoutFile) throws IOException {
        Pattern nextFocusUp = Pattern.compile("android:nextFocusUp", Pattern.MULTILINE);
        Pattern nextFocusDown = Pattern.compile("android:nextFocusDown", Pattern.MULTILINE);
        Pattern nextFocusLeft = Pattern.compile("android:nextFocusLeft", Pattern.MULTILINE);
        Pattern nextFocusRight = Pattern.compile("android:nextFocusRight", Pattern.MULTILINE);
        LineIterator iter = FileUtils.lineIterator(layoutFile);

        while (iter.hasNext()) {
            String row = iter.next();
            Matcher nextFocusUpMatcher = nextFocusUp.matcher(row);
            Matcher nextFocusDownMatcher = nextFocusDown.matcher(row);
            Matcher nextFocusLeftMatcher = nextFocusLeft.matcher(row);
            Matcher nextFocusRightMatcher = nextFocusRight.matcher(row);

            if (nextFocusUpMatcher.find()
                    || nextFocusDownMatcher.find()
                    || nextFocusLeftMatcher.find()
                    || nextFocusRightMatcher.find()) {
                return false;
            }

        }
        return true;
    }

}
