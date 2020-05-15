package university;

public class Student extends Person{
	
	private int matr;		//numero di matricola
	
	//CORSI SEGUITI DALLO STUDENTE
	private final static int MAX_COURSES = 25;	//numero massimo di corsi
	private Course[] Courses = new Course[MAX_COURSES];	//lista dei corsi seguiti
	private int AttendedCourses = 0;	//numero di corsi seguiti
	private int[] Grades = new int[MAX_COURSES];	//vettore dei voti ottenuti
	private int GradesNumber = 0;	//numero di corsi per cui ha sostenuto l'esame
	private int ExamsNumber = 0;	//numero di esami sostenuti
	private double Score = -1;		//punteggio in graduatoria
	
	//costruttore di Student
	public Student(){
		super();
		this.matr = 00000;
		for(int i=0; i<MAX_COURSES; i++)
			Grades[i] = -1 ;
	}
		
	//setter delle info dello studente
	public void setStudent(String name, String surname, int matr) {
		this.setInfo(name, surname);
		this.matr = matr;
	}
	
	//getter del numero di matricola
	public int getStudentMatr() {
		return this.matr;
	}
	
	//getter delle info dello studente
	public String getStudentInfo() {
		return (String.valueOf(this.matr) + " " + this.getInfo() );
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

	public boolean addCourseGrade(int courseID, int grade) {
		int index=0;
		//ricerca del corso all'interno del vettore
		for(Course c : Courses)
			if(c.getCod() == courseID)
				break;
			else index++;
		
		//nel caso in cui index sia uguale al numero di corsi sostenuti,
		//il corso non è presente nel vettore
		if(index >= AttendedCourses) {
			System.out.println("Il corso "+ courseID +" non è presente nella lista dello studente "+ this.matr+"/n");
			return false;
		}
		
		/* se il corso viene trovato all'interno del vettore, memorizzo il suo voto
		 * nel vettore Grades in posizione index, così da mantenenere la corrispondenza
		 * degli indici nei due vettori. 
		 * Il numero di corsi per cui ha sostenuto l'esame viene aggiornato solo nel caso
		 * in cui è la prima volta che sostiene l'esame per quel corso 
		 */
		this.ExamsNumber++;	//incremento del numero di esami sostenuti
		if(this.Grades[index] == -1)
			GradesNumber++;			
		this.Grades[index]=grade;
		
		return true;
		
	}
	
	//calcola la media di uno studente
	public double calculateAVG() {
		double sum = 0.0;		//somma dei voti
		
		//non ha sostenuto esami
		if(GradesNumber == 0)
			return -1.0;
		
		//percorro il vettore dei voti
		for(int g: Grades)
			if(g != -1)
				sum += g;
		
		return (sum / (double) GradesNumber);
	}
	
	//calcola il punteggio
	public void calculateScore() {
		if(AttendedCourses == 0)
			this.Score = -1;
		else
			this.Score = this.calculateAVG() + ( (double)ExamsNumber / (double)AttendedCourses)* 10.0;
	}

	public double getScore() {
		return Score;
	}

}



















