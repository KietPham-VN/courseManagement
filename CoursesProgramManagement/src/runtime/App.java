package runtime;

import data.EntityManager;
import ui.Menu;

public class App {

    public static void main(String[] args) {
        // khởi tạo mấy cái menu
        // menu chính
        Menu menu = new Menu("CoursesProgramManagement");
        menu.addNewOption("Manage the Topics");
        menu.addNewOption("Manage the Courses");
        menu.addNewOption("Manage the Learners");
        menu.addNewOption("Search information");
        menu.addNewOption("Save Topics, Courses and Learner to file");
        menu.addNewOption("Quit");

        // menu của topic
        Menu topicMenu = new Menu("Topic Management");
        topicMenu.addNewOption("Add Topics to catalog");
        topicMenu.addNewOption("Update Topic");
        topicMenu.addNewOption("Delete Topic");
        topicMenu.addNewOption("Display all Topics");

        // menu của course
        Menu courseMenu = new Menu("Course Management");
        courseMenu.addNewOption("Add Course");
        courseMenu.addNewOption("Update Course");
        courseMenu.addNewOption("Delete Course");
        courseMenu.addNewOption("Display Course information");
        courseMenu.addNewOption("Display all Course");

        // menu của learner
        Menu learnerMenu = new Menu("Learner Management");
        learnerMenu.addNewOption("Add Learner to Course");
        learnerMenu.addNewOption("Enter scores for learners");
        learnerMenu.addNewOption("Display Learner information");

        // menu search
        Menu searchMenu = new Menu("Search Menu");
        searchMenu.addNewOption("Search Topic");
        searchMenu.addNewOption("Search Course");
        Menu searchCourseMenu = new Menu("Search Course Menu");
        searchCourseMenu.addNewOption("Search course by course's name");
        searchCourseMenu.addNewOption("Search course by Topic's name");
        EntityManager em = new EntityManager();
        em.loadFromFile();
        while (true) {
            menu.print();
            int choice = menu.getChoice();
            switch (choice) {
                case 1: {
                    while (true) {
                        topicMenu.print();
                        choice = topicMenu.getChoice();
                        switch (choice) {
                            case 1: {
                                em.addTopic();
                                break;
                            }
                            case 2: {
                                em.updateTopic();
                                break;
                            }
                            case 3: {
                                em.removeTopic();
                                break;
                            }
                            case 4: {
                                em.showTopicsList();
                                break;
                            }
                        }
                        if (!Menu.isContinue()) {
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    while (true) {
                        courseMenu.print();
                        choice = courseMenu.getChoice();
                        switch (choice) {
                            case 1: {
                                em.addCourse();
                                break;
                            }
                            case 2: {
                                em.updateCourse();
                                break;
                            }
                            case 3: {
                                em.removeCourse();
                                break;
                            }
                            case 4: {
                                em.showCoursesList();
                                break;
                            }
                            case 5: {
                                em.displayAllCourse();
                                break;
                            }
                        }
                        if (!Menu.isContinue()) {
                            break;
                        }
                    }
                    break;
                }
                case 3: {
                    while (true) {
                        learnerMenu.print();
                        choice = topicMenu.getChoice();
                        switch (choice) {
                            case 1: {
                                em.addLearner();
                                break;
                            }
                            case 2: {
                                em.enterSrcoreForLearner();
                                break;
                            }
                            case 3: {
                                em.showLearnerList();

                                break;
                            }
                        }
                        if (!Menu.isContinue()) {
                            break;
                        }
                    }
                    break;
                }
                case 4: {
                    while (true) {
                        searchMenu.print();
                        choice = searchMenu.getChoice();
                        switch (choice) {
                            case 1: {
                                em.searchTopicByName();
                                break;
                            }
                            case 2: {
                                while (true) {
                                    searchCourseMenu.print();
                                    choice = searchCourseMenu.getChoice();
                                    switch (choice) {
                                        case 1: {
                                            em.searchCourseByName();
                                            break;
                                        }
                                        case 2: {
                                            em.searchCourseByTopic();
                                            break;
                                        }
                                    }
                                    if (!Menu.isContinue()) {
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (!Menu.isContinue()) {
                            break;
                        }
                    }
                    break;
                }
                case 5: {
                    System.out.println("All file has been saved");
                    em.saveToFile();
                    break;
                }
                default: {
                    System.out.println("See you again <3");
                    em.saveToFile();
                    return;
                }
            }
        }
    }
}
