package objects;

public class Student {
private String fname;
private String lname;
private String stuID;
private String helper;
private String timeLeft;
private String email;

public Student(String fname, String lname, String stuID, String helper, String timeLeft, String email) {
	this.fname = fname;
	this.lname = lname;
	this.stuID = stuID;
	this.helper = helper;
	this.timeLeft = timeLeft;
	this.email = email;
}
public String getFname() {
	return fname;
}
public void setFname(String fname) {
	this.fname = fname;
}
public String getLname() {
	return lname;
}
public void setLname(String lname) {
	this.lname = lname;
}
public String getStuID() {
	return stuID;
}
public void setStuID(String stuID) {
	this.stuID = stuID;
}
public String getHelper() {
	return helper;
}
public void setHelper(String helper) {
	this.helper = helper;
}
public String getTimeLeft() {
	return timeLeft;
}
public void setTimeLeft(String timeLeft) {
	this.timeLeft = timeLeft;
}
	
}
