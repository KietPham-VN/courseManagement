package data;

class Topic extends Entity implements Comparable<Topic> {

    // properties
    private String type;
    private String title;
    private int duration;

    // constuctor
    public Topic(String uniqueID, String name, String type, String title, int duration) {
        super(uniqueID, name);
        this.type = type;
        this.title = title;
        this.duration = duration;
    }

    // getter
    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    // setter
    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    // other method
    @Override
    public String toString() {
        String str = String.format("|%-6s|%-30s|%-5s|%-50s|%8s|",
                super.uniqueID, super.name, this.type, this.title, this.duration);
        return str;
    }

    @Override
    public int compareTo(Topic that) {
        return this.name.compareTo(that.name);
    }
}
