package it.unisa.aDoctor.smellDetectionRules;

import java.io.IOException;

import it.unisa.aDoctor.beans.ClassBean;

public class DroppedDataRule {

    public boolean isDroppedDataRule(ClassBean pClassBean) throws IOException {

        if (pClassBean.getTextContent().contains("EditText")) {
            if (!pClassBean.getTextContent().contains("onSaveInstanceState")) {
                return true;
            }
        }

        return false;
    }
}
