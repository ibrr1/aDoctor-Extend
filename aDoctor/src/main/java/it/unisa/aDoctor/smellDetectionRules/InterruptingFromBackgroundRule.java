package it.unisa.aDoctor.smellDetectionRules;

import java.io.IOException;

import it.unisa.aDoctor.beans.ClassBean;

public class InterruptingFromBackgroundRule {

    public boolean isInterruptingFromBackgroundRule(ClassBean pClassBean) throws IOException {

        if (pClassBean.getTextContent().contains("extends Service")) {
            if (pClassBean.getTextContent().contains("startActivity") || pClassBean.getTextContent().contains("Toast") || pClassBean.getTextContent().contains("AlertDialog")) {
                return true;
            }
        }
        return false;
    }
}
