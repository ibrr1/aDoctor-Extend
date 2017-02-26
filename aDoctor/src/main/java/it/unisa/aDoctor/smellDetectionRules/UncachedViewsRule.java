package it.unisa.aDoctor.smellDetectionRules;

import java.io.IOException;

import it.unisa.aDoctor.beans.ClassBean;

public class UncachedViewsRule {

    public boolean isUncachedViewsRule(ClassBean pClassBean) throws IOException {

        if (pClassBean.getTextContent().contains("ListView") || pClassBean.getTextContent().contains("ViewPager") || pClassBean.getTextContent().contains("RecyclerView") || pClassBean.getTextContent().contains("ViewFlipper") ) {
            if (!pClassBean.getTextContent().contains("ViewHolder")) {
                return true;
            }
        }

        return false;
    }
}
