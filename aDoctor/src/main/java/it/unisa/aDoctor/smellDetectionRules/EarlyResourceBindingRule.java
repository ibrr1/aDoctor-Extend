package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class EarlyResourceBindingRule {

    public boolean isEarlyResourceBindingRule(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            if (method.getTextContent().contains("LocationManager")) {
                for (MethodBean call : method.getMethodCalls()) {
                    if (!call.getName().equals("onResume")) {
                        return true;
                    }
                }
            }
        }
        
        

        return false;

    }
}
