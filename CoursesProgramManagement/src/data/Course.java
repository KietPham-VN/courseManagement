package data;

class Course extends Entity {

    // properties
    private String type;
    private String title;
    private String beginDate;
    private String endDate;
    private double passPercentage;
    private String status;
    private double tuitionFee;
    private String topic;
    private int size;
    private int maxSize;

    // constructor
    public Course(String uniqueID, String name, String type, String topic, String title,
            String beginDate, String endDate, double passPercentage,
            String status, double tuitionFee, int size, int maxSize) {
        super(uniqueID, name);
        this.type = type;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.passPercentage = passPercentage;
        this.status = status;
        this.tuitionFee = tuitionFee;
        this.topic = topic;
        this.size = size;
        this.maxSize = maxSize;
    }

    // getter
    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getPassPercentage() {
        return passPercentage;
    }

    public String getStatus() {
        return status;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public String getTopic() {
        return topic;
    }

    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return maxSize;
    }

    // setter
    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPassPercentage(double passPercentage) {
        this.passPercentage = passPercentage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    // other function
    @Override
    public String toString() {
        String str = String.format(EntityManager.COURSE_PRINT_PATTERN.replace("%n", ""),
                super.uniqueID, super.name, this.type, this.topic, this.title, this.beginDate, this.endDate,
                this.status, this.tuitionFee, this.passPercentage, this.size, this.maxSize
        );
        return str;
    }
}
