package main.java.JustDialMultiServiceThreadsControlledParallelStream;

public class Data {
	
	private String name;
	private String phone;
	private String address;
	private String rating;
	private String votes;
	
	public static final String firstLineForExcel = "\"Name\",\"Phone\",\"Address\",\"Rating\",\"Votes\"\n";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		phone = phone.replace('(', ' ');
		phone = phone.replace(')', ' ');
		phone = phone.replace('-', ' ');
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getVotes() {
		return votes;
	}
	public void setVotes(String votes) {
		this.votes = votes;
	}
	
	public String toStringForExcel() {
		return "\"" + name + "\",\"" + phone + "\",\"" + address + "\",\"" + rating + "\",\"" + votes + "\"\n";
	}
	
	@Override
	public String toString() {
		return name + "," + phone + "," + address + "," + rating + "," + votes;
	}
	
}
