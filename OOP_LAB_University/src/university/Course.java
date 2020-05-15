package university;

public class Course {
	
	private String Title;	//titolo del corso
	private Person Teacher;	//dati anagrafici del docente
	private int Cod;		//codice del corso
	
	//STUDENTI CHE SEGUONO IL CORSO
	private final static int MAX_STUDENTS = 100;	//numero massimo di studenti
	private Student[] Students = new Student[MAX_STUDENTS];	//lista degli studenti frequentanti
	private int AttendingStudents = 0;	//numero di studenti frequentanti
	private int[] Grades = new int[MAX_STUDENTS];	//voti degli studenti iscritti al corso
	private int GradesNumber = 0;	//numero di voti ottenuti
	
	//costruttore di Course	
	public Course() {
		this.Title = new String();
		this.Teacher = new Person();
		for(int i=0; i<MAX_STUDENTS; i++)
			Grades[i] = -1 ;
	}

	//setter delle info del corso
	public void setInfo(String title, String teacher, int cod) {
		this.Title = new String(title);
		String dettails[] = teacher.split(" ");
		this.Teacher.setInfo(dettails[0], dettails[1]);		
		this.Cod = cod;
	}

	//getter del codice del corso
	public int getCod() {
		return this.Cod;
	}
	
	//getter del nome del corso
	public String getCourseTitle() {
		return this.Title;
	}
	
	//getter delle info del corso
	public String getInfo() {
		return (this.Cod + ", " + this.Title + ", " + this.Teacher.getInfo());
	}
	
	//aggiunta di uno studente al corso
	public void addStudent(Student student) {
		this.Students[AttendingStudents++] = student;
		
	}
	
	//regitrazione voto di uno studente iscritto al corso
	public boolean addStudentGrade(int studentId, int grade) {
		int index=0;
		//ricerca dello studente all'interno del vettore
		for(Student s : Students)
			if(s.getStudentMatr() == studentId)
				break;
			else index++;
		
		//nel caso in cui index sia uguale al numero di studenti del corso,
		//lo studente non è presente nel vettore
		if(index >= AttendingStudents) {
			System.out.println("Lo studente "+ studentId +" non è iscritto al corso "+ this.Cod+"/n");
			return false;
		}
		
		/* se lo studente viene trovato all'interno del vettore, memorizzo il suo voto
		 * nel vettore Grades in posizione index, così da mantenenere la corrispondenza
		 * degli indici nei due vettori.
		 * Il numero di studenti che hanno sostenuto l'esame viene aggiornato solo nel caso
		 * in cui lo studente in questine è la prima volta che sostiene l'esame.
		 */
		if(this.Grades[index] == -1)
			GradesNumber++;			
		this.Grades[index]=grade;
		
		return true;
	}

	public StringBuffer printStudents() {
		StringBuffer list = new StringBuffer();
		for(int i=0; i<this.AttendingStudents; i++)
			list.append("\n" + Students[i].getStudentInfo() );
		return list;
	}
	
	
	//calcola la media di uno studente
	public double calculateAVG() {
		double sum = 0.0;		//somma dei voti
			
		//nessuno studente ha sostenuto esami
		if(GradesNumber == 0)
			return -1.0;
			
		//percorro il vettore dei voti
		for(int g: Grades)
			if(g != -1)
				sum += g;
			
		return (sum / (double) GradesNumber);
	}

}