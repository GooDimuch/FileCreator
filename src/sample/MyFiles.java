package sample;

import java.io.File;

public class MyFiles {

    private File file;
    private long startIndex;
    private long finalIndex;

    public MyFiles(String filePath, String startAddress, String finalAddress) {
        this.file = new File(filePath);
        startIndex = Long.parseLong(startAddress, 16);
        finalIndex = Long.parseLong(finalAddress, 16) + 1;
    }

    public File getFile() {
        return file;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public long getFinalIndex() {
        return finalIndex;
    }
}
