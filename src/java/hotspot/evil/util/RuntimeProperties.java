package hotspot.evil.util;

public class RuntimeProperties {
    public static final int VM_ARCH_DATA_MODEL;
    public static final String VM_NAME;
    public static final String OS_ARCH;
    public static final Endian CPU_ENDIAN;
    public static final int OBJECT_HEADER_SIZE;
    
    static {
        VM_ARCH_DATA_MODEL = getArchDataModel();
        VM_NAME = getVMName();
        OS_ARCH = getOSArch();
        CPU_ENDIAN = Endian.current();
        OBJECT_HEADER_SIZE = Unsafes.getObjectHeaderSize();
    }
    
    private static int getArchDataModel() {
        String archDataModelStr = System.getProperty("sun.arch.data.model");
        int archDataModel;
        try {
            archDataModel = Integer.parseInt(archDataModelStr);
        } catch (NumberFormatException nfe) {
            archDataModel = -1;
        }
        return archDataModel;
    }
    
    private static String getVMName() {
        return System.getProperty("java.vm.name");
    }
    
    private static String getOSArch() {
        return System.getProperty("os.arch");
    }
    
    public static boolean isHotSpotVM() {
        return VM_NAME != null && VM_NAME.contains("HotSpot");
    }
    
    public static boolean isJRockitVM() {
        return VM_NAME != null && VM_NAME.contains("JRockit");
    }
    
    public static boolean isJ9VM() {
        return VM_NAME != null && VM_NAME.contains("IBM J9 VM");
        
    }
}
