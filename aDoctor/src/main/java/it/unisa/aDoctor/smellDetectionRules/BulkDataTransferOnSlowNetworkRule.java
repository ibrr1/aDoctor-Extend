package it.unisa.aDoctor.smellDetectionRules;

import java.io.IOException;

import it.unisa.aDoctor.beans.ClassBean;

public class BulkDataTransferOnSlowNetworkRule {

    public boolean isBulkDataTransferOnSlowNetworkRule(ClassBean pClassBean) throws IOException {

        if (pClassBean.getTextContent().contains("HttpPost")) {
            if (!pClassBean.getTextContent().contains("ConnectivityManager")) {
                return true;
            }
        }

        return false;
    }
}
