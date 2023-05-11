/**
 * This class represents a file.
 * Each file is saved in a folder.
 * Each file has a name, a file extension and a content which is of String type.
 */
public class File extends StorageItem {
    protected String fileExtension;
    protected String contentInFile;


    /**
     * The only goal of this constructor is just to pass the name of the new folder to the StorageItem class.
     * We are calling the StorageItem class (parent class) to set the name of the folder,
     * that we received from th Folder class(child class).
     * @param nameOfTheFolder stands for the name of the new folder.
     */
    public File(String nameOfTheFolder) {
        super(nameOfTheFolder);
    }

    /**
     * Constructor to initialize a new file.
     * @param fileName stands for setting the name of the new file.
     * @param fileExtension stands for setting the file extension of the new file.
     */
    public File(String fileName, String fileExtension){
        super(fileName);
        this.fileExtension = fileExtension;
        this.contentInFile = "";
    }

    /**
     * concat the mame of  the file + its file extension.
     * @return name.fileExtension
     */
    public String getName() {return getNameOfTheItem() + "." + this.fileExtension;}

    /**
     * The size of a file is determined to be the number of characters that are in it.
     * @return the size (length) of the specify file.
     */
    @Override
    public int getSize(){
        return contentInFile.length();
    }

    /**
     * Add content to the file.
     * Update the size of the storage item.
     * @param contentToAdd stands for the content that is added.
     */
    void addContent(String contentToAdd){
        this.contentInFile += contentToAdd;
        setSizeOfTheItem(getSize());
    }

    /**
     * Print the Name of the file + its file extension, its size, its creation date and its content.
     */
    void printContent(){
        System.out.println(getName() + " Size: " + getSize() + "MB Created: " +
                getCreationDateOfTheItem());
        System.out.println(this.contentInFile);
    }
}