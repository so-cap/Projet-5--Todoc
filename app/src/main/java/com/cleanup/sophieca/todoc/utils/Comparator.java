package com.cleanup.sophieca.todoc.utils;

import com.cleanup.sophieca.todoc.model.Employee;
import com.cleanup.sophieca.todoc.model.Task;

/**
 * Created by SOPHIE on 02/03/2020.
 */
public class Comparator {

    /**
     * Comparator to sort items from A to Z
     */
    public static class AZComparator implements java.util.Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Task) {
                return ((Task) o1).getName().compareTo(((Task)o2).getName());
            } else
                return ((Employee) o1).getLastName().compareTo(((Employee)o2).getLastName());
        }
    }

    /**
     * Comparator to sort items from Z to A
     */
    public static class ZAComparator implements java.util.Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Task) {
                return ((Task) o2).getName().compareTo(((Task)o1).getName());
            } else
                return ((Employee) o2).getLastName().compareTo(((Employee)o1).getLastName());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements java.util.Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.getCreationTimestamp() - left.getCreationTimestamp());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements java.util.Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.getCreationTimestamp() - right.getCreationTimestamp());
        }
    }
}
