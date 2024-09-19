package data;

class Learner extends Entity {

    // properties
    private String dateOfBirth;
    private double score;
    private String course;
    private String status;

    public Learner(String uniqueID, String name, String dateOfBirth, String course, double score, String status) {
        super(uniqueID, name);
        this.dateOfBirth = dateOfBirth;
        this.score = score;
        this.course = course;
        this.status = status;
    }

    // getter
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public double getScore() {
        return score;
    }

    public String getCourse() {
        return course;
    }

    public String getStatus() {
        return status;
    }

    // setter
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // other method
    @Override
    public String toString() {
        String str = String.format(EntityManager.LEARNER_PATTERN.replace("%n", ""),
                super.uniqueID, super.name, this.dateOfBirth, this.course, this.score, this.status);
        return str;
    }

}
