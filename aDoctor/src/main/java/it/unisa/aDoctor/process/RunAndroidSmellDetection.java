package it.unisa.aDoctor.process;

import it.unisa.aDoctor.smellDetectionRules.DurableWakeLockRule;
import it.unisa.aDoctor.smellDetectionRules.InefficientDataFormatAndParserRule;
import it.unisa.aDoctor.smellDetectionRules.SlowLoopRule;
import it.unisa.aDoctor.smellDetectionRules.NoLowMemoryResolverRule;
import it.unisa.aDoctor.smellDetectionRules.MemberIgnoringMethodRule;
import it.unisa.aDoctor.smellDetectionRules.InefficientDataStructureRule;
import it.unisa.aDoctor.smellDetectionRules.InternalGetterSetterRule;
import it.unisa.aDoctor.smellDetectionRules.DataTransmissionWithoutCompressionRule;
import it.unisa.aDoctor.smellDetectionRules.DebuggableReleaseRule;
import it.unisa.aDoctor.smellDetectionRules.InefficientSQLQueryRule;
import it.unisa.aDoctor.smellDetectionRules.LeakingInnerClassRule;
import it.unisa.aDoctor.smellDetectionRules.RigidAlarmManagerRule;
import it.unisa.aDoctor.smellDetectionRules.PublicDataRule;
import it.unisa.aDoctor.smellDetectionRules.LeakingThreadRule;
import it.unisa.aDoctor.smellDetectionRules.UnclosedCloseableRule;
import it.unisa.aDoctor.smellDetectionRules.InterruptingFromBackgroundRule;
import it.unisa.aDoctor.smellDetectionRules.UnnecessaryPermissionRule;
import it.unisa.aDoctor.smellDetectionRules.BulkDataTransferOnSlowNetworkRule;
import it.unisa.aDoctor.smellDetectionRules.DroppedDataRule;
import it.unisa.aDoctor.smellDetectionRules.EarlyResourceBindingRule;
import it.unisa.aDoctor.smellDetectionRules.TrackingHardwareIdRule;
import it.unisa.aDoctor.smellDetectionRules.UncachedViewsRule;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;
import it.unisa.aDoctor.beans.PackageBean;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.eclipse.core.runtime.CoreException;

import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;

public class RunAndroidSmellDetection {

    private static final String NEW_LINE_SEPARATOR = "\n";
    public static String[] FILE_HEADER;
    public static ArrayList<String> failedApps = new ArrayList<String>();
    public static ArrayList<String> countApps = new ArrayList<String>();
    public static int totalApps = 0;

    // The folder contains the set of Android apps that need to be analyzed
    public static void main(String[] args) throws IOException, CoreException {

        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Started at " + ft.format(new Date()));

        // Folder containing android apps to analyze
        File experimentDirectory = FileUtils.getFile(args[0]);
        File fileName = new File(args[1]);
        String smellsNeeded = args[2];
        System.out.println("smellsNeeded: " + smellsNeeded);
        System.out.println("Folder Name: " + experimentDirectory);

        FILE_HEADER = new String[StringUtils.countMatches(smellsNeeded, "1") + 4];

        DataTransmissionWithoutCompressionRule dataTransmissionWithoutCompressionRule = new DataTransmissionWithoutCompressionRule();
        DebuggableReleaseRule debbugableReleaseRule = new DebuggableReleaseRule();
        DurableWakeLockRule durableWakeLockRule = new DurableWakeLockRule();
        InefficientDataFormatAndParserRule inefficientDataFormatAndParserRule = new InefficientDataFormatAndParserRule();
        InefficientDataStructureRule inefficientDataStructureRule = new InefficientDataStructureRule();
        InefficientSQLQueryRule inefficientSQLQueryRule = new InefficientSQLQueryRule();
        InternalGetterSetterRule internaleGetterSetterRule = new InternalGetterSetterRule();
        LeakingInnerClassRule leakingInnerClassRule = new LeakingInnerClassRule();
        LeakingThreadRule leakingThreadRule = new LeakingThreadRule();
        MemberIgnoringMethodRule memberIgnoringMethodRule = new MemberIgnoringMethodRule();
        NoLowMemoryResolverRule noLowMemoryResolverRule = new NoLowMemoryResolverRule();
        PublicDataRule publicDataRule = new PublicDataRule();
        RigidAlarmManagerRule rigidAlarmManagerRule = new RigidAlarmManagerRule();
        SlowLoopRule slowLoopRule = new SlowLoopRule();
        UnclosedCloseableRule unclosedCloseableRule = new UnclosedCloseableRule();

        // Extra code smells check
        InterruptingFromBackgroundRule interruptingFromBackgroundRule = new InterruptingFromBackgroundRule();
        UnnecessaryPermissionRule unnecessaryPermissionRule = new UnnecessaryPermissionRule();
        BulkDataTransferOnSlowNetworkRule bulkDataTransferOnSlowNetworkRule = new BulkDataTransferOnSlowNetworkRule();
        DroppedDataRule droppedDataRule = new DroppedDataRule();
        EarlyResourceBindingRule earlyResourceBindingRule = new EarlyResourceBindingRule();
        TrackingHardwareIdRule trackingHardwareIdRule = new TrackingHardwareIdRule();
        UncachedViewsRule uncachedViewsRule = new UncachedViewsRule();

        String[] smellsType = {"DTWC", "DR", "DW", "IDFP", "IDS", "ISQLQ", "IGS", "LIC", "LT", "MIM", "NLMR", "PD", "RAM", "SL", "UC", "IFB", "UP", "BDTOSN", "DD", "ERB", "NIOOIMT", "THI", "UV"};

        FILE_HEADER[0] = "App Name";
        FILE_HEADER[1] = "Tag";
        FILE_HEADER[2] = "Tag Name";
        FILE_HEADER[3] = "Class";

        int headerCounter = 4;

        for (int i = 0; i < smellsNeeded.length(); i++) {
            if (smellsNeeded.charAt(i) == '1') {
                FILE_HEADER[headerCounter] = smellsType[i];
                headerCounter++;

            } else {

            }
        }

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        FileWriter fileWriter = new FileWriter(fileName);
        try (CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat)) {
            csvFilePrinter.printRecord((Object[]) FILE_HEADER);

            for (File project : experimentDirectory.listFiles()) {

                if (!project.isHidden()) {

                    System.out.println("============================");
                    System.out.println("Main Project: " + project.getName());
                    System.out.println("============================");

                    File f = new File(project.getAbsoluteFile().toString() + "/build.gradle");
                    File f2 = new File(project.getAbsoluteFile().toString() + "/settings.gradle");
                    File f3 = new File(project.getAbsoluteFile().toString() + "/AndroidManifest.xml");
                    File f4 = new File(project.getAbsoluteFile().toString() + "/gradlew.bat");
                    File f5 = new File(project.getAbsoluteFile().toString() + "/default.properties");

                    if (f.exists() || f2.exists() || f3.exists() || f4.exists() || f5.exists()) {
                        totalApps++;

                    } else {
                        try {
                            totalApps += Files.list(Paths.get(project.getAbsoluteFile().toString())).count();
                        } catch (Exception e) {
                            System.out.println("Exception: " + e);
                        }
                    }

                    File project2 = FileUtils.getFile(project.getAbsoluteFile());
                    try {
                        for (File subProject : project2.listFiles()) {
                            System.out.println("subProject: " + subProject.getName());

                            if (!subProject.isHidden()) {

                                try {

                                    // Method to convert a directory into a set of java packages.
                                    ArrayList<PackageBean> packages = FolderToJavaProjectConverter.convert(subProject.getAbsolutePath());

                                    for (PackageBean packageBean : packages) {

                                        for (ClassBean classBean : packageBean.getClasses()) {

                                            List record = new ArrayList();

                                            record.add(project.getName());

                                            if (f.exists() || f2.exists() || f3.exists() || f4.exists() || f5.exists()) {
//                                              System.out.println("success");
                                                record.add(project.getName());

                                            } else {
//                                              System.out.println("fail");
                                                record.add(subProject.getName());

                                                File project3 = FileUtils.getFile(subProject.getAbsoluteFile());
                                                for (File subProject2 : project3.listFiles()) {
                                                    if (!subProject2.isHidden()) {
                                                        record.add(subProject2.getName());
                                                    }
                                                }

                                                System.out.println("-- Analyzing class: " + classBean.getBelongingPackage() + "." + classBean.getName());
                                                record.add(classBean.getBelongingPackage() + "." + classBean.getName());

                                                for (MethodBean method : classBean.getMethods()) {
                                                    
                                                        System.out.println("=============> "+method.getName());
                                                        
                                                        if (method.getName().equals("onResume")){
                                                            System.out.println("$$$$$$$$$$$$$$ found it");
                                                            
                                                        }else{
                                                        }

                                                }

                                                // 1
                                                if (smellsNeeded.charAt(0) == '1') {
                                                    if (dataTransmissionWithoutCompressionRule.isDataTransmissionWithoutCompression(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 2
                                                if (smellsNeeded.charAt(1) == '1') {
                                                    if (debbugableReleaseRule.isDebuggableRelease(RunAndroidSmellDetection.getAndroidManifest(project))) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 3
                                                if (smellsNeeded.charAt(2) == '1') {
                                                    if (durableWakeLockRule.isDurableWakeLock(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 4
                                                if (smellsNeeded.charAt(3) == '1') {
                                                    if (inefficientDataFormatAndParserRule.isInefficientDataFormatAndParser(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 5
                                                if (smellsNeeded.charAt(4) == '1') {
                                                    if (inefficientDataStructureRule.isInefficientDataStructure(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 6
                                                if (smellsNeeded.charAt(5) == '1') {
                                                    if (inefficientSQLQueryRule.isInefficientSQLQuery(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 7
                                                if (smellsNeeded.charAt(6) == '1') {
                                                    if (internaleGetterSetterRule.isInternalGetterSetter(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 8
                                                if (smellsNeeded.charAt(7) == '1') {
                                                    if (leakingInnerClassRule.isLeakingInnerClass(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 9
                                                if (smellsNeeded.charAt(8) == '1') {
                                                    if (leakingThreadRule.isLeakingThread(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 10
                                                if (smellsNeeded.charAt(9) == '1') {
                                                    if (memberIgnoringMethodRule.isMemberIgnoringMethod(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 11
                                                if (smellsNeeded.charAt(10) == '1') {
                                                    if (noLowMemoryResolverRule.isNoLowMemoryResolver(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 12
                                                if (smellsNeeded.charAt(11) == '1') {
                                                    if (publicDataRule.isPublicData(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 13
                                                if (smellsNeeded.charAt(12) == '1') {
                                                    if (rigidAlarmManagerRule.isRigidAlarmManager(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 14
                                                if (smellsNeeded.charAt(13) == '1') {
                                                    if (slowLoopRule.isSlowLoop(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 15
                                                if (smellsNeeded.charAt(14) == '1') {
                                                    if (unclosedCloseableRule.isUnclosedCloseable(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 16
                                                if (smellsNeeded.charAt(15) == '1') {
                                                    if (interruptingFromBackgroundRule.isInterruptingFromBackgroundRule(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 17
                                                if (smellsNeeded.charAt(16) == '1') {
                                                    if (unnecessaryPermissionRule.isUnnecessaryPermission(RunAndroidSmellDetection.getAndroidManifest(project))) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 18
                                                if (smellsNeeded.charAt(17) == '1') {
                                                    if (bulkDataTransferOnSlowNetworkRule.isBulkDataTransferOnSlowNetworkRule(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 19
                                                if (smellsNeeded.charAt(18) == '1') {
                                                    if (droppedDataRule.isDroppedDataRule(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 20
                                                if (smellsNeeded.charAt(19) == '1') {
                                                    if (earlyResourceBindingRule.isEarlyResourceBindingRule(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }
//                                                 21
                                                if (smellsNeeded.charAt(20) == '1') {
                                                    if (dataTransmissionWithoutCompressionRule.isDataTransmissionWithoutCompression(classBean)
                                                            || inefficientSQLQueryRule.isInefficientSQLQuery(classBean)
                                                            || bulkDataTransferOnSlowNetworkRule.isBulkDataTransferOnSlowNetworkRule(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                // 22
                                                if (smellsNeeded.charAt(21) == '1') {
                                                    if (trackingHardwareIdRule.isTrackingHardwareIdRule(RunAndroidSmellDetection.getAndroidManifest(project), classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }
                                                // 23 uncachedViewsRule
                                                if (smellsNeeded.charAt(22) == '1') {
                                                    if (uncachedViewsRule.isUncachedViewsRule(classBean)) {
                                                        record.add("1");
                                                    } else {
                                                        record.add("0");
                                                    }
                                                }

                                                csvFilePrinter.printRecord(record);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exception: " + e);
                                    failedApps.add(project.getName());
                                    FileWriter writer = new FileWriter("FailedApps.csv", true);
                                    writer.append(project.getName());
                                    writer.append('\n');
                                    writer.close();

                                }

                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Exception: " + e);
                    }
                }
            }
        }
        System.out.println("CSV file was created successfully!");
        System.out.println("Finished at " + ft.format(new Date()));
        System.out.println("number of apps " + totalApps);
        System.out.println("Number of failed apps: " + failedApps.size());
    }

    public static File getAndroidManifest(File dir) {
        File androidManifest = null;
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            if (file.getName().equals("AndroidManifest.xml")) {
                androidManifest = file;
            }
        }
        return androidManifest;
    }

}
