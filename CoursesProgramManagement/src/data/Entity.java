package data;

abstract class Entity {

    // prop
    protected String uniqueID;
    protected String name;

    public Entity(String uniqueID, String name) {
        this.uniqueID = uniqueID;
        this.name = name;
    }

    // Getters
    public String getUniqueID() {
        return uniqueID;
    }

    public String getName() {
        return name;
    }
   
    // Setter
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
