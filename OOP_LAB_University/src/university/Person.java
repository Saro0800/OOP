package university;

public class Person {
	
	private String name, surname;	//nome e cognome della persona
	
	//costruttore
	public Person() {
		this.name = new String();
		this.surname = new String();
	}
	
	//setter dei dati anagrafici
	public void setInfo(String name, String Surname){
		this.name = new String(name);
		this.surname = new String(Surname);
	}
	
	//getter dei dati anagrafici
	public String getInfo() {
		return (name + " " +surname);
	}
	
}
