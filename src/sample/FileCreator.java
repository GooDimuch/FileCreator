package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileCreator {

    List<MyFiles> myFilesList;

    public FileCreator(List<MyFiles> myFilesList, String newFilePath) {
        this.myFilesList = myFilesList;

        File file = createNewFile(newFilePath);
        myFilesList.forEach(myFile -> appendFile(file,
                getBytesFromFile(myFile.getFile(), myFile.getStartIndex(), myFile.getFinalIndex())));
    }

    private File createNewFile(String filePath){
        File file = new File(filePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(new byte[0]);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void appendFile(File file, byte[] fileBArray) {
        try {
            Files.write(file.toPath(), fileBArray, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytesFromFile(File file, long startIndex, long finalIndex) {
        byte[] fileBArray = getAllBytesFromFile(file);
        assert fileBArray != null;
        return Arrays.copyOfRange(fileBArray, (int) startIndex, (int) finalIndex);
    }

    private byte[] getAllBytesFromFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
