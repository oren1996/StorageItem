import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class represents a storage item.
 * Each has a name, a creation time,
 a size and an array list represents a folder of storage items.
 */
public abstract class StorageItem implements Cloneable {
    protected String nameOfTheItem;
    protected Date creationDateOfTheItem;
    protected int sizeOfTheItem;
    protected ArrayList<StorageItem> currentFolder = new ArrayList<>();

    /**
     * Assign to the current folder.
     * @param currentFolder is the folder we are currently in.
     */
    public void setCurrentFolder(ArrayList<StorageItem> currentFolder) {
        this.currentFolder = currentFolder;
    }

    // Returns the name of the item. //
    public String getNameOfTheItem() {
        return nameOfTheItem;
    }


    /**
     * Set the the size of the item to the given size.
     * @param sizeOfTheItem the size that needs to be assigned.
     */
    public void setSizeOfTheItem(int sizeOfTheItem) {
        this.sizeOfTheItem = sizeOfTheItem;
    }


    //Returns the date that the item was created. //
    public Date getCreationDateOfTheItem() {
        return creationDateOfTheItem;
    }


    public StorageItem() {
    }

    /**
     * A constructor that initializes the name of the item and the date it was created.
     * Each item randomly gets a date between 2017/01/01 00:00:00 - 021/12/31 23:59:59.
     * @param name is the name of the item needs to be initialized.
     */
    public StorageItem(String name) {
        this.nameOfTheItem = name;
        String startingDay = "2017/01/01 00:00:00";
        LocalDateTime localDateTime1 = LocalDateTime.parse(startingDay,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );

        long minDateMilliseconds =
                localDateTime1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        String endingDate = "2021/12/31 23:59:59";
        LocalDateTime localDateTime2 = LocalDateTime.parse(endingDate,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );

        long maxDateMilliseconds =
                localDateTime2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        long range = maxDateMilliseconds - minDateMilliseconds;
        long aleatoryNumber = Math.abs(Main.rnd.nextLong());
        long dateInMilliseconds = (aleatoryNumber % range) + minDateMilliseconds;
        this.creationDateOfTheItem = new Timestamp(dateInMilliseconds);

    }

    //An abstract method implemented by the classes File and Folder.//
    public abstract int getSize();

    /**
     * A method that prints the tree path of a given item.</p>
     * The user pass the field of the sorting way that he wants to see the tree.
     * The sorting is done by name or size or creation date.
     * There is a secondary sorting- sorting by name.
     * The secondry sorting is used,
     in case the size or creation date of two items are equal.
     * @param field is the field the user wants to sort by.(name, size or creation date).
     */
    public void printTree(SortingField field) {
        int counter = 0;
        ArrayList<StorageItem> copyOfMainFolder =
                (ArrayList<StorageItem>) this.currentFolder.clone();
        System.out.println(getNameOfTheItem());
        switch (field) {
            case NAME:
                sortByName(this.currentFolder, counter);
                break;
            case SIZE:
                sortBySize(this.currentFolder, counter);
                break;
            case DATE:
                sortByDate(this.currentFolder, counter);
                break;
        }
        this.currentFolder = copyOfMainFolder;
    }


    /**
     * A method that sort by the name field in lexicography order.
     * @param localFolder represents the folder we are currently in.
     * @param counter counts the number of items in the folder.
     */
    public void sortByName(ArrayList<StorageItem> localFolder,int counter) {
        Collections.sort(localFolder, Comparator.comparing(StorageItem::
                getNameOfTheItem));
        for (int i = 0; i < localFolder.size(); i++) {
            if (localFolder.get(i).getClass() == File.class){
                for (int j = 0; j <= counter; j++) {
                    System.out.print("|    ");
                }
                System.out.println(((File) localFolder.get(i)).getName());
            }
            else {
                for (int j = 0; j <= counter; j++) {
                    System.out.print("|    ");
                }
                System.out.println(localFolder.get(i).getNameOfTheItem());
                Folder folderInFolder = (Folder) localFolder.get(i);
                this.currentFolder = folderInFolder.contentFolder;
                counter += 1;
                sortByName(this.currentFolder, counter);
                counter -= 1;
            }
        }

    }

    /**
     * A method that sort by the size of the items.
     * In case the sizes are the same, we sort by name.
     * @param localFolder represents the folder we are currently in.
     * @param counter counts the number of items in the folder.
     */
    public void sortBySize(ArrayList<StorageItem> localFolder,int counter) {
        Collections.sort(localFolder, Comparator.comparing(StorageItem::getSize).
                thenComparing(StorageItem::getNameOfTheItem));
        for (int i = 0; i < localFolder.size(); i++) {
            if (localFolder.get(i).getClass() == File.class){
                for (int j = 0; j <= counter; j++) {
                    System.out.print("|    ");
                }
                System.out.println(((File) localFolder.get(i)).getName());
            }
            else {
                for (int j = 0; j <= counter; j++) {
                    System.out.print("|    ");
                }
                System.out.println(localFolder.get(i).getNameOfTheItem());
                Folder folderInFolder = (Folder) localFolder.get(i);
                this.currentFolder = folderInFolder.contentFolder;
                counter += 1;
                sortBySize(this.currentFolder, counter);
                counter -= 1;
            }
        }

    }

    /**
     * A method that sort by the creation date of the items.
     * In case they are the same, we sort by name.
     * @param localFolder represents the folder we are currently in.
     * @param counter counts the number of items in the folder.
     */
    public void sortByDate(ArrayList<StorageItem> localFolder,int counter) {

        Collections.sort(localFolder, Comparator.comparing(StorageItem::
                getCreationDateOfTheItem).thenComparing(StorageItem::getNameOfTheItem));
        for (int i = 0; i < localFolder.size(); i++) {
            if (localFolder.get(i).getClass() == File.class){
                for (int j = 0; j <= counter; j++) {
                    System.out.print("|    ");
                }
                System.out.println(((File) localFolder.get(i)).getName());
            }
            else {
                for (int j = 0; j <= counter; j++) {
                    System.out.print("|    ");
                }
                System.out.println(localFolder.get(i).getNameOfTheItem());
                Folder folderInFolder = (Folder) localFolder.get(i);
                this.currentFolder = folderInFolder.contentFolder;
                counter += 1;
                sortByDate(this.currentFolder, counter);
                counter -= 1;
            }
        }
    }
}