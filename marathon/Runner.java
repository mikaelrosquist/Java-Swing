class Runner{
	private String name, country;
	private int startNo, age;
	private double time;

	public Runner(String name, String country, int age, int startNo, double time){
		this.name = name;
		this.age = age;
		this.country = country;
		this.startNo = startNo;
		this.time = time;
	}
	public int getStartNo(){
		return startNo;
	}
	public double getTime(){
		return time;
	}
	public String getName(){
		return name;
	}
	public int getAge(){
		return age;
	}
	public String getCountry() {
		return country;
	}
	public void setTime (double time){ //Metod för att ändra tiden
		this.time = time;
	}
}