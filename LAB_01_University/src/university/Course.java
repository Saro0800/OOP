package university;

public class Course {
	
	private String Title;	//titolo del corso
	private Person Teacher;	//dati anagrafici del docente
	private int Cod;		//codice del corso
	
	//STUDENTI CHE SEGUONO IL CORSO
	private final static int MAX_STUDENTS = 100;	//numero massimo di studenti
	private Student[] Students = new Student[MAX_STUDENTS];	//lista degli studenti frequentanti
	private int AttendingStudents = 0;	//numero di studenti frequentanti
	
	//costruttore di Course	
	public Course() {
		this.Title = new String();
		this.Teacher = new Person();
	}

	//setter delle info del corso
	public void setInfo(String title, String name, String surname, int cod) {
		this.Title = new String(title);
		this.Teacher.setInfo(name, surname);		
		this.Cod = cod;
	}

	//getter del codice del corso
	public int getCod() {
		return this.Cod;
	}
	
	//getter delle info del corso
	public String getInfo() {
		return (this.Cod + ", " + this.Title + ", " + this.Teacher.getInfo());
	}
	
	//aggiunta di uno studente al corso
	public void addStudent(Student student) {
		this.Students[AttendingStudents++] = student;
		
	}

	public StringBuffer printStudents() {
		StringBuffer list = new StringBuffer();
		for(int i=0; i<this.AttendingStudents; i++)
			list.append("\n" + Students[i].getStudentInfo() );
		return list;
	}
	
	
	
	
	
}
