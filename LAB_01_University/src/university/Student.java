package university;

public class Student {
	
	private Person Stud;	//dettagli anagrafici dello studente
	private int matr;		//numero di matricola
	
	//CORSI SEGUITI DALLO STUDENTE
	private final static int MAX_COURSES = 25;	//numero massimo di corsi
	private Course[] Courses = new Course[MAX_COURSES];	//lista dei corsi seguiti
	private int AttendedCourses = 0;	//numero di corsi seguiti
	
	//costruttore di Student
	public Student(){
		this.Stud = new Person();
		this.matr = 00000;
	}
		
	//setter delle info dello studente
	public void setStudent(String name, String surname, int matr) {
		this.Stud.setInfo(name, surname);
		this.matr = matr;
	}
	
	//getter del numero di matricola
	public int getStudentMatr() {
		return this.matr;
	}
	
	//getter delle info dello studente
	public String getStudentInfo() {
		return (String.valueOf(this.matr) + " " + this.Stud.getInfo() );
	}
	
	//aggiunta di un corso alla lista dei frequentati 
	public void addCourse(Course course) {
		this.Courses[ AttendedCourses++ ] = course;
	}
	
	//stampa dei corsi seguiti
	public StringBuffer printCourses() {
		StringBuffer list = new StringBuffer();
		for(int i=0; i<this.AttendedCourses; i++)
			list.append("\n"+Courses[i].getInfo());
		return list;
	}

}


