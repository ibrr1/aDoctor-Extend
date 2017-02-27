package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class EarlyResourceBindingRule {

    public boolean isEarlyResourceBindingRule(ClassBean pClass) {

        if (pClass.getTextContent().contains("LocationManager")) {
            if (!pClass.getTextContent().contains("onResume")) {
                return true;
            }
        }

        return false;

    }
}
