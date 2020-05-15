package university;


public class UniversityExt extends University {
	

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}
	
	//registra il voto di uno studente per un certo corso, e viceversa
	public void exam(int studentId, int courseID, int grade) {
		//controllo compatibilità dei parametri
		if(studentId - MATR_OFFSET<0 || studentId - MATR_OFFSET>=StudentsNumber) {
			System.out.println("Errore: matricola "+studentId+" non presente");
			return;
		}
		
		if(courseID - CODE_OFFSET<0 || courseID - CODE_OFFSET>=CoursesNumber) {
			System.out.println("Errore: corso "+courseID+" non presente");
			return;
		}
		
		//memorizzazione del voto nella lista del corso
		if(this.Courses[courseID-CODE_OFFSET].addStudentGrade(studentId, grade) == false)
			return;
		
		//memorizzazione del voto nella lista dello studente
		this.Students[studentId-MATR_OFFSET].addCourseGrade(courseID, grade);
		
		logger.info("Student " +studentId+ " took an exam in course " +courseID+ " with grade " +grade);
		
	}

	//resituisce la media di uno studente
	public String studentAvg(int studentId) {
		//controllo compatibilità dei parametri
		if(studentId - MATR_OFFSET<0 || studentId - MATR_OFFSET>=StudentsNumber) {
			System.out.println("Errore: matricola "+studentId+" non presente");
			return null;
		}
		
		double avg = this.Students[studentId - MATR_OFFSET].calculateAVG();
		
		if(avg == -1.0)
			return("Student "+studentId+" hasn't taken any exams");
		
		else 
			return("Student "+studentId+": "+avg);
		
	}
	
	public String courseAvg(int courseId) {
		//controllo compatibilità dei parametri
		if(courseId - CODE_OFFSET<0 || courseId - CODE_OFFSET>=CoursesNumber) {
			System.out.println("Errore: corso "+courseId+" non presente");
			return null;
		}
		
		double avg = this.Courses[courseId - CODE_OFFSET].calculateAVG();
		String title = this.Courses[courseId - CODE_OFFSET].getCourseTitle();
		
		if(avg == -1.0)
			return("No student has taken the exam in "+title);
		
		else 
			return("The average for the course "+title+" is: "+avg);
		
	}
	public void orderStudentsCopy(int[] matr) {
		//calcolo dello score di tutti gli studenti
			for(int i=0; i<StudentsNumber; i++)
				this.Students[i].calculateScore();
				
			//copia dei numeri di matricola
			for(int i=0; i<StudentsNumber; i++)
				matr[i] = this.Students[i].getStudentMatr() - MATR_OFFSET;
				
			//oridnamento ascendente del vettore in base allo score ottenuto
			for(int i=0; i<StudentsNumber; i++)
				for(int j=0; j<StudentsNumber-i-1; j++)
					if(this.Students[ matr[j] ].getScore() < this.Students[ matr[j+1] ].getScore() ) {
						int temp = matr[j];
						matr[j] = matr[j+1];
						matr[j+1] = temp;
					}
			
			return;
		
	}
	
	public String topThreeStudents() {
		int[] matr = new int[StudentsNumber];	//vettore contenente gli indici del vettore studenti
		StringBuffer list = new StringBuffer();
		
		orderStudentsCopy(matr);	//funzione di ordinamento
		
		for(int i=0; i<StudentsNumber && i<3; i++) {
			if(this.Students[ matr[i] ].getScore() >= 0) {
				list.append(this.Students[ matr[i] ].getInfo());
				list.append(" : ");
				list.append(this.Students[ matr[i] ].getScore());
				list.append("\n");
			}
			
		}
			
		return list.toString();
	}
}




















