package university;

import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	protected final static Logger logger = Logger.getLogger("University");
	
	private final String name;	//nome dell'università
	private Person Rector;		//rettore
	
	//INFORMAZIONI SUGLI STUDENTI	
	protected final static int MAX_STUDENTS = 1000;	//numero max di studenti
	protected final static int MATR_OFFSET = 10000;	//offset del numero di matricola
	protected Student[] Students = new Student[MAX_STUDENTS];	//array di studenti
	protected int StudentsNumber = 0;		//numero di studenti presenti in università
	
	//informazioni sui corsi
	protected final static int MAX_COURSES = 50;	//numero max di corsi per università
	protected final static int CODE_OFFSET = 10;	//offset del codice dei corsi
	protected Course[] Courses = new Course[MAX_COURSES];	//array di corsi 
	protected int CoursesNumber = 0;	//numero di corsi tenut in università

	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name = new String(name);
		this.Rector = new Person();
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last){
		Rector.setInfo(first, last);
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return
	 */
	public String getRector(){
		return this.Rector.getInfo();
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return
	 */
	public int enroll(String first, String last){
		Students[StudentsNumber] = new Student();
		Students[StudentsNumber].setStudent(first, last, StudentsNumber + MATR_OFFSET);
		StudentsNumber++;
				
		logger.info("New student enrolled: "+ (StudentsNumber-1+MATR_OFFSET) + ", " + first + " " + last);
		
		return Students[StudentsNumber-1].getStudentMatr();
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		return Students[id - MATR_OFFSET].getStudentInfo();
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		Courses[CoursesNumber] = new Course();
		Courses[CoursesNumber].setInfo(title, teacher, CODE_OFFSET + CoursesNumber);
		int cod = Courses[CoursesNumber].getCod();
		CoursesNumber++;
		
		logger.info("New course activated: "+ (CODE_OFFSET + CoursesNumber-1) + ", " + title);
		return cod;
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		return (Courses[code - CODE_OFFSET].getInfo());
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		//ATTRIBUZIONE CORSO -> STUDENTE
		Courses[courseCode - CODE_OFFSET].addStudent(Students[studentID - MATR_OFFSET]);
				
		//ATTRIBUZIONE STUDENTE -> CORSO
		Students[studentID - MATR_OFFSET].addCourse(Courses[courseCode - CODE_OFFSET]);
		
		logger.info("Student " +studentID+ " signed up for course " +courseCode);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		StringBuffer str = Courses[courseCode - CODE_OFFSET].printStudents();
		return str.toString();

	}

	/**
	 * Retrieves the study plan for a student
	 * 
	 * @param studentID id of the student
	 * @return list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		StringBuffer str = Students[studentID - MATR_OFFSET].printCourses();
		return str.toString();
	}
}
