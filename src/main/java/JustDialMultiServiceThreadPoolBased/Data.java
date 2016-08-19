package JustDialMultiServiceThreadPoolBased;

public class Data {
	
	private String name;
	private String phone;
	private String address;
	
	public static final String firstLineForExcel = "\"Name\",\"Phone\",\"Address\"\n\n";
	
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
	
	public String toStringForExcel() {
		return "\"" + name + "\",\"" + phone + "\",\"" + address + "\"\n";
	}
	
	@Override
	public String toString() {
		return name + "," + phone + "," + address;
	}
	
}
