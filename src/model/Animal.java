package model;

public class Animal {
    private final int id;
    private final String animalName;
    private String additionalInfo, ownerName, ownerPhone;
    private boolean allergic;

    public Animal(int id, String animalName, String ownerName, String ownerPhone, String additionalInfo, boolean allergic) {
        this.animalName = animalName;
        this.additionalInfo = additionalInfo;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.id = id;
        this.allergic = allergic;
    }

    public Animal( int id, String animalName, String ownerName, String ownerPhone, String additionalInfo) {
        this.animalName = animalName;
        this.additionalInfo = additionalInfo;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.id = id;
        this.allergic = false;
    }

    public int getId() {
        return id;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public boolean isAllergic() {
        return allergic;
    }

    public void setAllergic(boolean allergic) {
        this.allergic = allergic;
    }
}
