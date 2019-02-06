package com.ourproject.mohankumardhakal.agroproject.HelperClasses;

public class WorkerInfo {
    String worker_name;
    String contact_no;
    String address;

    public WorkerInfo(String contact_no, String address, String start_date, String salary) {
        this.contact_no = contact_no;
        this.address = address;
        this.start_date = start_date;
        this.salary = salary;
    }

    String start_date;
    String salary;



    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
