package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import utils.Inputter;
import utils.StringProcessor;

public class EntityManager {

    // mấy cái đường dẫn đến file txt trên máy em 
    private static final String TOPIC_URL = "D:\\LearningMaterial\\FPTU\\LAB211\\Project\\CoursesProgramManagement\\topics.txt";
    private static final String COURSE_URL = "D:\\LearningMaterial\\FPTU\\LAB211\\Project\\CoursesProgramManagement\\courses.txt";
    private static final String LEARNER_URL = "D:\\LearningMaterial\\FPTU\\LAB211\\Project\\CoursesProgramManagement\\learners.txt";

    // những cái pattern để in ra cho đẹp
    private static final String TOPIC_PATTERN = "|%-6s|%-30s|%-5s|%-50s|%-8s|%n";
    private static final String COURSE_PATTERN = "|%-4s|%-30s|%-7s|%-6s|%-35s|%-10s|%-10s|%-8s|%11s|%15s|%4s|%8s|%n";
    private static final String LEARNER_PATTERN = "|%-6s|%-20s|%-10s|%-8s|%-6s|%-6s|%n";

    // cái mảng lưu tất cả mọi Entity
    ArrayList<Entity> myList = new ArrayList<>();

    // các hàm search
    // hàm nhận id tìm vị trí của Entity trong danh sách
    public int searchEntityIndexById(String keyId) {
        for (int i = 0; i <= myList.size() - 1; i++) {
            if (myList.get(i).getUniqueID().equalsIgnoreCase(keyId)) {
                return i;
            }
        }
        return -1;
    }

    // hàm nhận id tìm Entity tương ứng
    public Entity searchEntityById(String keyId) {
        int pos = searchEntityIndexById(keyId);
        return pos == -1 ? null : myList.get(pos);
    }

    // hàm nhận formatId để tìm ra topic course hay leaner tương ưng và bỏ vào mảng
    public ArrayList searchEntityByIdFormat(String format) {
        ArrayList<Entity> newList = new ArrayList<>();
        myList.stream().filter((entity) -> (entity.uniqueID.contains(format))).forEachOrdered((entity) -> {
            newList.add(entity);
        });
        return newList;
    }

    public void searchTopicByName() {
        ArrayList<Topic> newList = new ArrayList<>();
        String topicName = Inputter.getString("Enter topic's name: ", "That field is required!");

        // Lọc các chủ đề và thêm vào newList
        for (Entity entity : myList) {
            if (entity.getUniqueID().startsWith("T")) {
                Topic topic = (Topic) entity;
                if (topic.getName().toLowerCase().contains(topicName.toLowerCase())) {
                    newList.add(topic);
                }
            }
        }
        if (newList.isEmpty()) {
            System.out.println("No topic found!!");
            return;
        }
        System.out.println(newList.size() + " topic(s) found");
        showTopicsList(newList); // Hiển thị danh sách
    }

    public ArrayList searchTopicByName(String topicName) {
        ArrayList<Topic> newList = new ArrayList<>();
        // Lọc các chủ đề và thêm vào newList
        for (Entity entity : myList) {
            if (entity.getName().toLowerCase().contains(topicName.toLowerCase())) {
                newList.add((Topic) entity);
            }
        }
        return newList;
    }

    // hàm search dựa trên topic
    public void searchCourseByTopic() {
        String topicName = Inputter.getString("Enter topic's name: ", "That field is required!");
        ArrayList<Topic> topicsFound = searchTopicByName(topicName);
        ArrayList<Course> result = new ArrayList<>();

        for (Entity entity : myList) {
            for (Topic topic : topicsFound) {
                if (entity.getUniqueID().startsWith("C")) {
                    Course course = (Course) entity;
                    if (course.getTopic().equals(topic.getUniqueID())) {
                        result.add(course);
                    }
                }
            }
        }
        if (result.isEmpty()) {
            System.out.println("Nothing to print");
            return;
        }
        System.out.println(result.size() + " course(s) found");
        showCoursesList(result);
    }

    public void searchCourseByName() {
        ArrayList<Course> courseList = new ArrayList<>();
        String courseName = Inputter.getString("Enter Course's name: ", "That field is required!");
        for (Entity entity : myList) {
            if (entity.getUniqueID().startsWith("C")) {
                Course course = (Course) entity;
                if (course.getName().toLowerCase().contains(courseName.toLowerCase())) {

                    courseList.add(course);
                }
            }
        }
        showCoursesList(courseList);
    }

    // hàm xóa 
    public void removeTopic() {
        if (myList.isEmpty()) {
            System.out.println("Deletion can not be performed as the list is empty");
            return;
        }

        String keyId = Inputter.getString("Input topic's ID you want to remove: ", "That field is required!");
        Entity entity = searchEntityById(keyId);

        entity = (Topic) entity;
        System.out.println("The topic information:");
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        System.out.printf(TOPIC_PATTERN, "ID", "Name", "Type", "Title", "Duration");
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        System.out.println(entity);
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));

        if (isConfirmed()) {
            myList.remove(entity);
            System.out.println("The removal process was successful");
        } else {
            System.out.println("Operation canceled " + keyId + " was not removed.");
        }
    }

    public void removeCourse() {
        String keyId = Inputter.getString("Input course's ID you want to remove: ", "That field is required!");
        Entity entity = searchEntityById(keyId);
        entity = (Course) entity;
        System.out.println("The course information:");
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
        System.out.printf(COURSE_PATTERN, "ID", "Name", "Topic", "Type", "Title", "Begin Date", "End Date", "Status", "Tuition Fee", "Pass Percentage", "Size", "Max size");
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
        System.out.println(entity);
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));

        if (isConfirmed()) {
            myList.remove(entity);
            System.out.println("The removal process was successful");
        } else {
            System.out.println("Operation canceled " + keyId + " was not removed.");
        }
    }

// các hàm in nè
// hàm in ra các topic
    public void showTopicsList() {
        ArrayList topicsList = searchEntityByIdFormat("T");
        if (topicsList.isEmpty()) {
            System.out.println("Nothing to print");
            return;
        }

        // sort lại theo tên
        Collections.sort(topicsList);

        // in ra
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        System.out.printf(TOPIC_PATTERN, "ID", "Name", "Type", "Title", "Duration");
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        topicsList.forEach((topic) -> {
            System.out.println(topic);
        });
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
    }

    public void showTopicsList(ArrayList topicsList) {
        if (topicsList.isEmpty()) {
            System.out.println("Nothing to print");
            return;
        }

        // sort lại theo tên
        Collections.sort(topicsList);
        // in ra
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        System.out.printf(TOPIC_PATTERN, "ID", "Name", "Type", "Title", "Duration");
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        topicsList.forEach((topic) -> {
            System.out.println(topic);
        });
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
    }

    // hàm in ra các course
    public void showCoursesList() {
        ArrayList coursesList = searchEntityByIdFormat("C");
        if (coursesList.isEmpty()) {
            System.out.println("Nothing to print");
            return;
        }

        // sort lại theo beginDate
        Comparator<Course> orderByBeginDate = (Course c1, Course c2) -> c1.getBeginDate().compareTo(c2.getBeginDate());
        Collections.sort(coursesList, orderByBeginDate);

        // in ra
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
        System.out.printf(COURSE_PATTERN, "ID", "Name", "Topic", "Type", "Title", "Begin Date", "End Date", "Status", "Tuition Fee", "Pass Percentage", "Size", "Max size");
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
        coursesList.forEach((course) -> {
            System.out.println(course);
        });
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
    }

    public void showCoursesList(ArrayList coursesList) {
        if (coursesList.isEmpty()) {
            System.out.println("Nothing to print");
            return;
        }

        // sort lại theo beginDate
        Comparator<Course> orderByBeginDate = (Course c1, Course c2) -> c1.getBeginDate().compareTo(c2.getBeginDate());
        Collections.sort(coursesList, orderByBeginDate);

        // in ra
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
        System.out.printf(COURSE_PATTERN, "ID", "Name", "Topic", "Type", "Title", "Begin Date", "End Date", "Status", "Tuition Fee", "Pass Percentage", "Size", "Max size");
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
        coursesList.forEach((course) -> {
            System.out.println(course);
        });
        StringProcessor.printLine(StringProcessor.extractNumbers(COURSE_PATTERN));
    }

    // hàm in ra các learner
    public void showLearnerList() {
        ArrayList<Learner> leanersList = searchEntityByIdFormat("L");

        if (leanersList.isEmpty()) {
            System.out.println("No learner to print");
            return;
        }

        // in ra 
        StringProcessor.printLine(StringProcessor.extractNumbers(LEARNER_PATTERN));
        System.out.printf(LEARNER_PATTERN, "ID", "Name", "Birthday", "Course", "Score", "Status");
        StringProcessor.printLine(StringProcessor.extractNumbers(LEARNER_PATTERN));
        leanersList.forEach((learner) -> {
            System.out.println(learner);
        });
        StringProcessor.printLine(StringProcessor.extractNumbers(LEARNER_PATTERN));
    }

    public void displayAllCourse() {
        System.out.println("__________Course List__________");
        ArrayList<Course> coursesList = searchEntityByIdFormat("C");
        if (coursesList.isEmpty()) {
            System.out.println("Nothing to print");
            return;
        }
        int count = 1;
        Comparator<Course> orderByBeginDate = (Course c1, Course c2) -> c1.getBeginDate().compareTo(c2.getBeginDate());
        Collections.sort(coursesList, orderByBeginDate);
        for (Course course : coursesList) {
            System.out.printf("%d. %s %n", count, course.getName());
            count++;
        }
    }

    // các hàm add
    // addTopic
    public void addTopic() {
        String nId;
        boolean isDup;
        do {
            isDup = false;
            nId = Inputter.getString("Enter topic's Id: ", "That field is required!");

            Entity entity = searchEntityById(nId);
            if (entity != null) {
                isDup = true;
                System.out.println("This id has been used ");
            }
        } while (isDup);

        String nName = Inputter.getString("Enter name: ", "That field is required!");
        String nType = Inputter.getString("Enter topic's type: ", "That field is required!");
        String nTitle = Inputter.getString("Enter topic's title: ", "That field is required!");
        int nDuration = Inputter.getAnIntegerWithLowerBound("Enter topic's duration: ", "That field is required!", 0);

        // tạo rồi thêm vào danh sách
        Topic nTopic = new Topic(nId, nName, nType, nTitle, nDuration);
        myList.add(nTopic);
        System.out.println("Adding topic is successful.");
    }

    public void addCourse() {
        String nId;

        // Kiểm tra trùng ID
        boolean isDup;
        do {
            isDup = false;
            nId = Inputter.getString("Enter course's Id: ", "That field is required!");

            Entity entity = searchEntityById(nId);
            if (entity != null) {
                isDup = true;
                System.out.println("This id has been used ");
            }
        } while (isDup);

        String nName = Inputter.getString("Enter name: ", "That field is required!");
        String nType = Inputter.getString("Enter course's type: ", "That field is required!");
        String nTitle = Inputter.getString("Enter course's title: ", "That field is required!");

        String nBeginDate, nEndDate;
        while (true) {
            nBeginDate = Inputter.getString("Enter course's begin date (yyyy-MM-dd): ", "That field is required!");
            nEndDate = Inputter.getString("Enter course's end date (yyyy-MM-dd): ", "That field is required!");
            try {
                if (StringProcessor.parseDate(nBeginDate, "yyyy-MM-dd").after(StringProcessor.parseDate(nEndDate, "yyyy-MM-dd"))) {
                    System.out.println("End date must be after the begin date. Please try again.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Date must be in the format of (yyyy-MM-dd)");
            }
        }

        double nPassPercentage = Inputter.getADouble("Enter course's pass percentage: ", "That field is required!", 0, 100);
        String nStatus = Inputter.getString("Enter course's status: ", "That field is required!");
        double nTuituionFee = Inputter.getADouble("Enter course's tuition fee: ", "That field is required!");

        Topic topic;
        String nTopicID;
        do {
            nTopicID = Inputter.getString("Enter topic's ID: ", "That field is required!");
            topic = (Topic) searchEntityById(nTopicID);
            if (topic == null) {
                System.out.println("No topic found with ID: " + nTopicID + ". Please try again.");
            }
        } while (topic == null);

        int nMaxSize = Inputter.getAnIntegerWithLowerBound("Enter course's max size: ", "That field is required!", 0);
        int nSize = Inputter.getAnInteger("Enter course's size: ", "That field is required!", 0, nMaxSize);

        Course nCourse = new Course(nId, nName, nType, nTopicID, nTitle, nBeginDate, nEndDate, nPassPercentage, nStatus, nTuituionFee, nSize, nMaxSize);
        myList.add(nCourse);
        System.out.println("Adding course is successful.");
    }

    public void addLearner() {
        String nId;

        // Kiểm tra trùng ID
        boolean isDup;
        do {
            isDup = false;
            nId = Inputter.getString("Enter learner's Id: ", "That field is required!");

            Entity entity = searchEntityById(nId);
            if (entity != null) {
                isDup = true;
                System.out.println("This id has been used ");
            }
        } while (isDup);

        String nName = Inputter.getString("Enter name: ", "That field is required!");
        // Thêm Learner
        String courseID;
        Course course;
        while (true) {
            courseID = Inputter.getString("Enter course's ID: ", "That field is required!");
            course = (Course) searchEntityById(courseID);
            if (course == null) {
                System.out.println("The course " + courseID + " is currently not existing");
            } else if (course.getSize() == course.getMaxSize()) {
                System.out.println("The course has reached its maximum capacity");
            } else {
                break;
            }
        }

        String nBirthday = Inputter.getString("Enter learner's birthday (yyyy-MM-dd): ", "That field is required!");
        double nScore = Inputter.getADouble("Enter learner's score: ", "That field is required!", 0, 100);
        String nStatus = Inputter.getString("Enter learner's status: ", "That field is required!");

        Learner nLearner = new Learner(nId, nName, nBirthday, courseID, nScore, nStatus);
        course.setSize(course.getSize() + 1);
        myList.add(nLearner);
        System.out.println("Adding learner is successful.");
    }

    // các hàm update
    public void updateTopic() {
        String uId = Inputter.getString("Enter Topic's Id: ", "That field is required!");

        // Tìm kiếm thực thể dựa vào ID
        Entity entity = searchEntityById(uId);
        if (entity == null) {
            System.out.println("The topic with ID: " + uId + " does not exist.");
            return;
        }

        // Cập nhật Topic
        Topic topic = (Topic) entity;
        //thông báo
        System.out.println("The topic information before updating");
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        System.out.printf(TOPIC_PATTERN, "ID", "Name", "Type", "Title", "Duration");
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        System.out.println(topic);
        StringProcessor.printLine(StringProcessor.extractNumbers(TOPIC_PATTERN));
        System.out.println("Updating.........");

        String uName = Inputter.getString("Enter topic's name or press Enter to skip: ");
        String uType = Inputter.getString("Enter topic's type or press Enter to skip: ");
        String uTitle = Inputter.getString("Enter topic's title or press Enter to skip: ");

        String uDuration;
        while (true) {
            try {
                uDuration = Inputter.getString("Enter topic's duration or press Enter to skip: ");
                break;
            } catch (NumberFormatException e) {
                System.out.println(e);
                System.out.println("Please enter a number!");
            }
        }
        if (isConfirmed()) {
            if (!uName.isEmpty()) {
                topic.setName(uName);
            }
            if (!uType.isEmpty()) {
                topic.setType(uType);
            }
            if (!uTitle.isEmpty()) {
                topic.setTitle(uTitle);
            }
            if (!uDuration.isEmpty()) {
                topic.setDuration(Integer.parseInt(uDuration));
            }
            System.out.println("Update successful");
        } else {
            System.out.println("Update canceled.");
        }
    }

    public void updateCourse() {
        String uId = Inputter.getString("Enter course's Id: ", "That field is required!");

        // Tìm kiếm thực thể dựa vào ID
        Entity entity = searchEntityById(uId);
        if (entity == null) {
            System.out.println("The course with ID: " + uId + " does not exist.");
            return;
        }

        Course course = (Course) entity;

        String tempName = Inputter.getString("Enter new name (leave empty to keep current value): ");
        String tempType = Inputter.getString("Enter new type (leave empty to keep current value): ");
        String tempTitle = Inputter.getString("Enter new title (leave empty to keep current value): ");

        // Kiểm tra topic có trong danh sách
        String tempTopicID;
        Topic tempTopic;
        do {
            tempTopicID = Inputter.getString("Enter new topic ID (leave empty to keep current value): ");
            if (tempTopicID.isEmpty()) {
                break; // Giữ nguyên topic cũ
            }
            tempTopic = (Topic) searchEntityById(tempTopicID);
            if (tempTopic == null) {
                System.out.println("No topic found with ID: " + tempTopicID + ". Please try again.");
            }
        } while (tempTopic == null);

        String tempBeginDate = course.getBeginDate();
        String tempEndDate = course.getEndDate();

        // Nhập mới endDate và kiểm tra các trường hợp, cho phép người dùng nhập lại nếu sai
        while (true) {
            String newEndDate = Inputter.getString("Enter new end date (leave empty to keep current value): ");
            String newBeginDate = Inputter.getString("Enter new begin date (leave empty to keep current value): ");

            try {
                if (newBeginDate.isEmpty() && newEndDate.isEmpty()) {
                    break; // Không thay đổi gì
                }

                if (newBeginDate.isEmpty()) {
                    // Kiểm tra nếu chỉ có endDate mới
                    if (StringProcessor.parseDate(tempBeginDate, "yyyy-MM-dd").after(StringProcessor.parseDate(newEndDate, "yyyy-MM-dd"))) {
                        System.out.println("End date must be after the current begin date (" + tempBeginDate + "). Please try again.");
                    } else {
                        tempEndDate = newEndDate;
                        break;
                    }
                } else if (newEndDate.isEmpty()) {
                    // Kiểm tra nếu chỉ có beginDate mới
                    if (StringProcessor.parseDate(newBeginDate, "yyyy-MM-dd").after(StringProcessor.parseDate(tempEndDate, "yyyy-MM-dd"))) {
                        System.out.println("Begin date must be before the current end date (" + tempEndDate + "). Please try again.");
                    } else {
                        tempBeginDate = newBeginDate;
                        break;
                    }
                } else {
                    // Kiểm tra nếu cả hai được nhập mới
                    if (StringProcessor.parseDate(newBeginDate, "yyyy-MM-dd").after(StringProcessor.parseDate(newEndDate, "yyyy-MM-dd"))) {
                        System.out.println("End date must be after the begin date. Please try again.");
                    } else {
                        tempBeginDate = newBeginDate;
                        tempEndDate = newEndDate;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Date must be in the format of (yyyy-MM-dd). Please try again.");
            }
        }

        // Kiểm tra passPercentage và tuitionFee
        String tempPassPercentageStr = Inputter.getString("Enter new pass percentage (leave empty to keep current value): ");
        double tempPassPercentage = course.getPassPercentage();
        while (!tempPassPercentageStr.isEmpty()) {
            try {
                tempPassPercentage = Double.parseDouble(tempPassPercentageStr);
                if (tempPassPercentage >= 0 && tempPassPercentage <= 100) {
                    break;
                } else {
                    System.out.println("Please enter a valid pass percentage (0-100)!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid percentage.");
            }
            tempPassPercentageStr = Inputter.getString("Enter new pass percentage (leave empty to keep current value): ");
        }

        String tempTuitionFeeStr = Inputter.getString("Enter new tuition fee (leave empty to keep current value): ");
        double tempTuitionFee = course.getTuitionFee();
        while (!tempTuitionFeeStr.isEmpty()) {
            try {
                tempTuitionFee = Double.parseDouble(tempTuitionFeeStr);
                if (tempTuitionFee >= 0) {
                    break;
                } else {
                    System.out.println("Please enter a valid tuition fee (>= 0)!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
            tempTuitionFeeStr = Inputter.getString("Enter new tuition fee (leave empty to keep current value): ");
        }

        // Kiểm tra size và maxSize
        int tempSize = course.getSize();
        int tempMaxSize = course.getMaxSize();
        while (true) {
            String tempMaxSizeStr = Inputter.getString("Enter new max size (leave empty to keep current value): ");
            if (!tempMaxSizeStr.isEmpty()) {
                tempMaxSize = Integer.parseInt(tempMaxSizeStr);
            }
            String tempSizeStr = Inputter.getString("Enter new size (leave empty to keep current value): ");
            if (!tempSizeStr.isEmpty()) {
                tempSize = Integer.parseInt(tempSizeStr);
            }
            if (tempSize <= tempMaxSize) {
                break; // Size hợp lệ
            } else {
                System.out.println("Size must be less than or equal to max size. Please try again.");
            }
        }

        // Kiểm tra status
        String tempStatus = Inputter.getString("Enter new status (leave empty to keep current value): ");

        // Xác nhận trước khi cập nhật
        if (isConfirmed()) {
            if (!tempName.isEmpty()) {
                course.setName(tempName);
            }
            if (!tempType.isEmpty()) {
                course.setType(tempType);
            }
            if (!tempTitle.isEmpty()) {
                course.setTitle(tempTitle);
            }
            course.setTopic(tempTopicID);
            course.setBeginDate(tempBeginDate);
            course.setEndDate(tempEndDate);
            course.setPassPercentage(tempPassPercentage);
            course.setTuitionFee(tempTuitionFee);
            course.setSize(tempSize);
            course.setMaxSize(tempMaxSize);
            if (!tempStatus.isEmpty()) {
                course.setStatus(tempStatus);
            }
            System.out.println("Course updated successfully.");
        } else {
            System.out.println("Update canceled.");
        }
    }

    public void enterSrcoreForLearner() {
        String uId;
        Learner learner;
        double score;
        while (true) {
            uId = Inputter.getString("Enter learner's ID: ", "That field is required!");
            StringProcessor.printLine(StringProcessor.extractNumbers(LEARNER_PATTERN));
            System.out.printf("|%-6s|%-20s|%-10s|%-8s|%-6s|%n", "ID", "Name", "Birthday", "Course", "Score");
            StringProcessor.printLine(StringProcessor.extractNumbers(LEARNER_PATTERN));

            learner = (Learner) searchEntityById(uId);
            System.out.println(learner.toString());
            StringProcessor.printLine(StringProcessor.extractNumbers(LEARNER_PATTERN));

            score = Inputter.getADouble("Enter Student's score: ", "That field is required!", 0, 10);

            if (isConfirmed()) {
                learner.setScore(score);
                System.out.println("Score has been set");
                System.out.println("Update successful");
            } else {
                System.out.println("Update canceled.");
            }
        }
    }

    // hàm xác nhận dùng khi có update hoặc xóa
    public boolean isConfirmed() {
        String confirmation;
        while (true) {
            confirmation = Inputter.getString("Are you sure(y/n)? ", "That field is required");
            if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                return true;
            } else if (confirmation.equalsIgnoreCase("n") || confirmation.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Please enter y/n or yes/no.");
            }
        }
    }

    // method đọc topic từ file
    public boolean loadTopicFromFile(String url) {
        File file = new File(url);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                StringTokenizer st = new StringTokenizer(line, "|");
                String uniqueID = st.nextToken().trim().toUpperCase();
                String name = st.nextToken().trim();
                String type = st.nextToken().trim();
                String title = st.nextToken().trim();
                int duration = Integer.parseInt(st.nextToken().trim());
                Topic topic = new Topic(uniqueID, name, type, title, duration);
                myList.add(topic);
                line = reader.readLine();
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Loading for topic.txt has failed");

            return false;
        }
    }

    // hàm load course từ file
    public boolean loadCourseFromFile(String url) {
        File file = new File(url);
        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                StringTokenizer st = new StringTokenizer(line, "|");
                String uniqueID = st.nextToken().trim().toUpperCase();
                String name = st.nextToken().trim();
                String type = st.nextToken().trim();
                String topic = st.nextToken().trim();
                String title = st.nextToken().trim();
                String beginDate = st.nextToken().trim();
                String endDate = st.nextToken().trim();
                String status = st.nextToken().trim();
                double passPercentage = Double.parseDouble(st.nextToken().trim());
                double tuitionFee = Double.parseDouble(st.nextToken().trim());
                int size = Integer.parseInt(st.nextToken().trim());
                int maxSize = Integer.parseInt(st.nextToken().trim());
                Course course = new Course(uniqueID, name, type, topic, title, beginDate, endDate, passPercentage, status, tuitionFee, size, maxSize);
                myList.add(course);
                line = reader.readLine();
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Loading for course.txt has failed");
            return false;
        }
    }

    // method load learner file
    public boolean loadLearnerFromFile(String url) {
        File file = new File(url);
        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {

                StringTokenizer st = new StringTokenizer(line, "|");
                String uniqueID = st.nextToken().trim().toUpperCase();
                String name = st.nextToken().trim();
                String dateOfBirth = st.nextToken().trim();

                String course = st.nextToken().trim();
                double score = Double.parseDouble(st.nextToken().trim());

                String status = st.nextToken().trim();
                Learner learner = new Learner(uniqueID, name, dateOfBirth, course, score, status);
                myList.add(learner);
                line = reader.readLine();
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Loading for learner.txt has failed");
            return false;
        }
    }

    // method lưu topic vào file 
    public boolean saveTopicToFile(String url) {
        File file = new File(url);
        try {
            ArrayList<Topic> topicsList = searchEntityByIdFormat("T");
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
            for (Topic topic : topicsList) {
                writer.write(topic.toString());
                writer.write("\n");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Flie error: " + e);
            return false;
        }
    }

    // method lưu course vào file 
    public boolean saveCourseToFile(String url) {
        File file = new File(url);
        try {
            ArrayList<Course> coursesList = searchEntityByIdFormat("C");
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
            for (Course course : coursesList) {
                writer.write(course.toString().replaceAll("%", ""));
                writer.write("\n");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Flie error: " + e);
            return false;
        }
    }

    // method lưu learner vào file 
    public boolean saveLearnerToFile(String url) {
        File file = new File(url);
        try {
            ArrayList<Learner> learnersList = searchEntityByIdFormat("L");

            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
            for (Learner learner : learnersList) {
                writer.write(learner.toString());
                writer.write("\n");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Flie error: " + e);
            return false;
        }
    }

    // hàm lưu 
    public void saveToFile() {
        if (saveTopicToFile(TOPIC_URL) && saveCourseToFile(COURSE_URL) && saveLearnerToFile(LEARNER_URL)) {
            System.out.println("All files have been saved");
        }
    }

    // hàm load
    public void loadFromFile() {
        if (loadTopicFromFile(TOPIC_URL) && loadCourseFromFile(COURSE_URL) && loadLearnerFromFile(LEARNER_URL)) {
            System.out.println("All files have been loaded");
        }
    }
}
