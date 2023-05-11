import java.util.ArrayList;

/**
 * This class represents a folder.
 * Each folder is an ArrayList of StorageItem type.
 */
public class Folder extends File implements Cloneable {
    protected ArrayList<StorageItem> contentFolder = new ArrayList<>();


    /**
     * Constructor to initialize a new folder
     * We receive the name of the folder that we are then transmitting to the Folder class.
     * We are initializing the content of the new folder.
     * @param nameOfTheFolder stands for setting the name of the new folder.
     */
    public Folder(String nameOfTheFolder) {
        super(nameOfTheFolder);
        setCurrentFolder(this.contentFolder);
        this.contentFolder.clear();
    }


    /**
     * We are getting the size of a folder.
     * The size of a folder is determined to be the sum of the size of all the files.
     * @return the size (length) of the specify folder
     * It calls the function recursiveGetSize() which is the function that is calculating the size of a folder
     */
    @Override
    public int getSize() {
        int size = 0;
        ArrayList<StorageItem> copyOfMainFolder = (ArrayList<StorageItem>) this.contentFolder.clone();
        size += recursiveGetSize(size, this.contentFolder);
        this.contentFolder = copyOfMainFolder;
        return size;
    }

    /**
     * As we don't know how many folders there are in each folder, we use a recursive function.
     * @param countSize     stands for counting the number of characters in each files.
     * @param currentFolder stands for knowing in which folder we are in.
     * @return the size of a folder (sum of all characters in files).
     */
    public int recursiveGetSize(int countSize, ArrayList<StorageItem> currentFolder) {
        for (int i = 0; i < currentFolder.size(); i++) {
            if (currentFolder.get(i) instanceof File) {
                countSize += ((File) currentFolder.get(i)).getSize();
            } else {
                Folder folderInFolder = (Folder) this.contentFolder.get(i);
                this.contentFolder = folderInFolder.contentFolder;
                countSize = recursiveGetSize(countSize, this.contentFolder);
            }
        }
        return countSize;
    }


    /**
     * If the new item is not in the folder, we add it, else we do not.
     * Note that it is possible to have two files which have the same name but a different file extension.
     * @param item stands for the new item that we want to add to the folder.
     * @return true if the new item is not in the folder, false otherwise.
     */
    boolean addItem(StorageItem item) {
        for (int i = 0; i < this.contentFolder.size(); i++) {
            if (item instanceof Folder) {
                if (this.contentFolder.get(i).nameOfTheItem.equals(item.nameOfTheItem)) {
                    return false;
                }
            } else {
                if (this.contentFolder.get(i).nameOfTheItem.equals(item.nameOfTheItem)) {
                    if (((File) this.contentFolder.get(i)).fileExtension.equals((((File) item).fileExtension))) {
                        return false;
                    }
                }
            }
        }
        this.contentFolder.add(item);
        return true;
    }


    /**
     * We are checking the path for a given item.
     * @param path stands for the path that we need to check
     * @return the file if it exists, else return null.
     */
    File findFile(String path) {
        ArrayList<StorageItem> copyOfMainFolder = (ArrayList<StorageItem>) this.contentFolder.clone();
        while (true) {
            if (path.contains("/")) {
                int x = path.indexOf("/");
                for (int i = 0; i < this.contentFolder.size(); i++) {
                    if (path.substring(0, x).equals(this.contentFolder.get(i).getNameOfTheItem())) {
                        Folder folderInFolder = (Folder) this.contentFolder.get(i);
                        this.contentFolder = folderInFolder.contentFolder;
                    }
                }
                path = path.substring(x + 1);
            } else {
                for (int i = 0; i < this.contentFolder.size(); i++) {
                    if ((((File) contentFolder.get(i)).getName()).equals(path)) {
                            File file = (File) this.contentFolder.get(i);
                            this.contentFolder = copyOfMainFolder;
                            return file;
                        }
                    }
                    this.contentFolder = copyOfMainFolder;
                    return null;
                }
            }
        }
    }