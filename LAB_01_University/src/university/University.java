package university;

public class University {
	
	private final String name;	//nome dell'università
	private Person Rector;		//rettore
	
	//INFORMAZIONI SUGLI STUDENTI	
	private final static int MAX_STUDENTS = 1000;	//numero max di studenti
	private final static int MATR_OFFSET = 10000;	//offset del numero di matricola
	private Student[] Students = new Student[MAX_STUDENTS];	//array di studenti
	private int StudentsNumber = 0;		//numero di studenti presenti in università
	
	//informazioni sui corsi
	private final static int MAX_COURSES = 50;	//numero max di corsi per università
	private final static int CODE_OFFSET = 10;	//offset del codice dei corsi
	private Course[] Courses = new Course[MAX_COURSES];	//array di corsi 
	private int CoursesNumber = 0;	//numero di corsi tenut in università
	
	//Constructor di University
	public University(String name) {
		this.name = new String(name);
		this.Rector = new Person();
	}
	
	//getter del nome dell'università
	public String getName() {
		return name;
	}
	
	//setter del nome del rettore
	public void setRector(String name, String surname) {
		Rector.setInfo(name, surname);
	}
	
	//getter del nome del rettore
	public String getRector(){
		return this.Rector.getInfo();
	}
	
	//aggiunta di uno studente
	public int enroll(String name, String surname) {
		Students[StudentsNumber] = new Student();
		Students[StudentsNumber].setStudent(name, surname, StudentsNumber + MATR_OFFSET);
		StudentsNumber++;
		return Students[StudentsNumber-1].getStudentMatr();
	}
	
	//ricerca di uno studente
	public String student(int matr) {
		return Students[matr - MATR_OFFSET].getStudentInfo();
	}
	
	//aggiunta di un corso
	public int activate(String Title, String name, String surname){
		Courses[CoursesNumber] = new Course();
		Courses[CoursesNumber].setInfo(Title, name, surname,CODE_OFFSET + CoursesNumber);
		int cod = Courses[CoursesNumber].getCod();
		CoursesNumber++;
		return cod;
	}
	
	//ricerca di un corso
	public String course(int cod){
		return (Courses[cod - CODE_OFFSET].getInfo());
	}
	
	//registrazione di uno studente ad un corso
	public void register(int ID, int cod) {
		
		//ATTRIBUZIONE CORSO -> STUDENTE
		Courses[cod - CODE_OFFSET].addStudent(Students[ID - MATR_OFFSET]);
		
		//ATTRIBUZIONE STUDENTE -> CORSO
		Students[ID - MATR_OFFSET].addCourse(Courses[cod - CODE_OFFSET]);
	}
	
	//stampa la lista degli studenti che frequentano un corso
	public StringBuffer listAttendees(int cod) {
		return Courses[cod - CODE_OFFSET].printStudents();
	}
	
	//stampa la lista di corsi 
	public StringBuffer studyPlan(int ID) {
		return Students[ID - MATR_OFFSET].printCourses();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}
