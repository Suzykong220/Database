import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * this class construct a database of student records.
 * @author Scarlet
 *
 */
public class Database {
	
	private ArrayList<Student> students;
	private ArrayList<Integer> IDs;
	private static int countStudent;
	private static Scanner in = new Scanner(System.in);
	private static final String MENU = "\nDATABASE OF STUDENTS!\n=====================\n" +
			"Choose from the menu of options:\n1. Print the student details.\n"+
			"2. Search for a student.\n3. Add a student record.\n" +
			"4. Delete a student record.\n5. Update a student record.\n" +
			"6. Save to a file.\n7. Exit the database.\n\nEnter your option: ";
	
	public Database (ArrayList<Student> students) {
		this.students = students;
		this.IDs = new ArrayList<Integer>(0);
	}
	
	public static void main(String[] args) {
		// 1. input file
		try { 
			String inFilename = args[0];
			int option = -1;
			// check for execution one or two
			boolean execution1 = true;
			if (args.length == 2) {
				execution1 = false;
			} 
			
			// (1) execution one 
			if (execution1 == true) {
				
				// (a) read file
				Database d1 = readFile(inFilename);
				
			//  (b) menu options
			while (option != 7 ) {
				option = OptionMenu();
				// execute option
			switch (option) {
			case 1: print(d1);
			break;
			
			// search students
			case 2: 
				System.out.print("\nSearch based on ID(1), name(2), age(3), course(4): ");
				try {
					int optionForSearch = in.nextInt();
					in.nextLine();
					if (optionForSearch > 4 || optionForSearch < 1 )
						throw new IncorrectOptionForSearch();
					searchStudent(optionForSearch, d1);
				} catch (Exception e) {
					System.out.println("INCORRECT OPTION FOR SEARCH.");
					in.nextLine();
				}
			break;
			
			// add students
			case 3:
				try {
			ArrayList<String> info = new ArrayList<String>(0);
			System.out.print("Enter the student's name: ");
			in.nextLine(); // change
			info.add(in.nextLine()); //change
			System.out.print("Enter the student's year of birth: ");
			info.add(in.next());
			System.out.print("Enter the number of courses: ");
			int num = in.nextInt();
			for (int i = 0; i < num; i ++) {
				System.out.print("Enter the details of course " + (i + 1)
						+ " in the below format."
						+ "\n<Course department> <course number> <credits>: ");
				for (int n = 0; n < 3; n++) {
					info.add(in.next());
				}
			}
			String trim = "";
			for (String s : info)
			{
			    trim += s + ",";
			}
			addStudent(trim,d1);
			System.out.println("Student has been added to the database.");
				} catch (Exception e) {
				System.out.println("INVALID OPTION FOR ADDING/REMOVING COURSES. COURSES NOT UPDATED.");
				in.nextLine();
			}
			break;
			
			// delete students
			case 4: 
				System.out.print("Enter the ID of the student to be removed: ");
				try {
				deleteStudents(in.nextInt(),d1);
				System.out.println("Student has been removed from the database.");
				} catch (Exception e) {
					System.out.println("STUDENT TO BE REMOVED WAS NOT FOUND IN THE DATABASE.");
					in.nextLine();
				}
			break;
			
			// update students
			case 5: 
			ArrayList<String> update = new ArrayList<String>(0);
			System.out.print("Enter the ID of the student to be updated: ");
			String ID = in.next();
				// validate id 
			try {
				searchID(Integer.parseInt(ID),d1);
				update.add(ID);
				System.out.print("Update name(1), year of birth(2), courses(3): ");
				int optionForSearch = in.nextInt();
				if (optionForSearch > 3 || optionForSearch < 1 )
					throw new IncorrectOptionForSearch();
				switch (optionForSearch) {
				case 1: update.add("name"); 
				System.out.print("Enter a new name: ");
				in.nextLine();
				update.add(in.nextLine());
				break;
				case 2: update.add("year"); 
				System.out.print("Enter a new year of birth: ");
				update.add(in.next());
				break;
				case 3: update.add("courses"); 
				System.out.print("Add(1) or Remove(2) a course: ");
				update.add(in.next());
				System.out.print("Enter the details of the course to be added.\n" +
					"<Course department> <course number> <credits>: ");
				for (int n = 0; n < 3; n++) {
					update.add(in.next());
				} 
				break;
				}
			String upd = "";
			for (String s : update)
			{
			    upd += s + ",";
			}
			d1.students = UpdateStudent(upd,d1);
			System.out.println("Student record has been updated.");

			} catch (InputMismatchException e) {
				System.out.println("INVALID OPTION FOR SEARCH.");
			} catch (IncorrectOptionForSearch e) {
				System.out.println("INVALID OPTION FOR SEARCH.");
			} catch (NoRecordForSearch e) {
				System.out.println("STUDENT TO BE UPDATED WAS NOT FOUND IN THE DATABASE.");
			} catch (NumberFormatException e) {
				System.out.println("STUDENT TO BE UPDATED WAS NOT FOUND IN THE DATABASE.");
			} catch (CourseExistException e) {
				System.out.println("THIS STUDENT IS ALREADY TAKING THIS COURSE.");
			} catch (CourseNotExistException e) {
				System.out.println("\nCOURSE TO BE REMOVED WAS NOT FOUND.");
			}
			break;
			
			// save
			case 6: 
				System.out.print("Enter the name of the output file: ");
				String outputF = in.next();
				save(outputF,d1);
				System.out.println("Details of the students are saved in a file named " +
				outputF + ".");
			break;
			
				//  exit
				case 7: System.out.print("\nDATABASE TERMINATED!");
				break;
				} // end of switch case in main menu
			
			}// while option is not 7
			
			in.close();
			
			}  else { 
				// execution two

				Database d2 = readFile(inFilename);
				String update = args[1];
				followInstruction(update,d2);
				save("output.txt",d2);
				System.out.println("Details of the students are saved in a file named output.txt.");

			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		

	}
	
	/**
	 * the main option menu
	 * @return
	 */
	public static int OptionMenu() {
		// input option and validate it 
		
		boolean validOption = false;
		int option = -1;
		while (validOption == false) {
			System.out.print(MENU);
			 try {
				 
				 option = in.nextInt();
				 validOption = validate(option);
			 } catch (InputMismatchException e) {
				 System.out.println("\nINVALID INPUT FOR MENU OPTIONS.");
				 in.nextLine();
			 }
		}
		return option;
	}

	/**
	 * validate the input option 
	 * @param option
	 * @return
	 */
	public static boolean validate(int option) {
		// TODO Auto-generated method stub
		if (option < 1 || option > 7 ) {
			throw new InputMismatchException();
		} else return true;
	}

	/**
	 * for execution2, deal with the update file.
	 * @param filename
	 * @param d2
	 * @throws FileNotFoundException
	 * @throws CourseExistException 
	 * @throws NumberFormatException 
	 * @throws CourseNotExistException 
	 * @throws NoRecordForSearch 
	 */
    public static void followInstruction(String filename, Database d2) 
    		throws FileNotFoundException, NumberFormatException, CourseExistException, CourseNotExistException, NoRecordForSearch {
		// create a new file
		File inputFile = new File(filename);
		
		// create variables to store instruction and the rest of the line
		int instruction = -1;
		ArrayList<String> rest = new ArrayList<String>();
		
		// create a new scanner
		Scanner inScan = new Scanner(inputFile);
		
		// store the content into array list
		countStudent = 0;
		while (inScan.hasNextLine()) {
			String Line = inScan.nextLine();
			String[] part = Line.split(",");
			instruction = Integer.parseInt(part[0]);
			rest = new ArrayList<String>(0);
			for (int i = 1; i < part.length; i++) {
				rest.add(part[i]);
			}
			String trim = "";
			for (String s : rest)
			{
			    trim += s + ",";
			}
			switch (instruction) {
			case 0: addStudent(trim,d2);
			System.out.println("Student has been added to the database.");
			break;
			case 1: try {
					deleteStudents(Integer.parseInt(trim.replaceAll(",", "")),d2);
					System.out.println("Student has been removed from the database.");
				} catch (Exception e) {
					System.out.println("STUDENT TO BE REMOVED WAS NOT FOUND IN THE DATABASE.");
				}
			break;
			case 2: try {
				UpdateStudent(trim.substring(0,trim.length()-1),d2);
				System.out.println("Student record has been updated.");
			} catch (NoRecordForSearch e) {
				System.out.println("STUDENT TO BE UPDATED WAS NOT FOUND IN THE DATABASE.");
			} catch (CourseExistException e){
				System.out.println("THIS STUDENT IS ALREADY TAKING THIS COURSE.");
			} catch (CourseNotExistException e) {
				System.out.println("COURSE TO BE REMOVED WAS NOT FOUND.");
			}
			break;
			}// end of switch cases
			}// end of while loop
		inScan.close();
	}
    
    /**
     * search student info using given id and database
     * @param n ID of the student to search
     * @param d current database
     */
    public static void searchStudent(int n, Database d) {
    	
		try {	
		switch (n) {
		case 1: 
			System.out.print("Enter the ID to be searched: ");
			int id = in.nextInt();
			Student s1 = searchID(id, d);
			System.out.println("\nSTUDENT WITH ID " + id + ":");
			System.out.println("==================");
			System.out.println("Name: " + s1.getName() + ", ID: " + id);
			break;
		case 2: 
			System.out.print("Enter the name to be searched: ");
			String name = in.next();
			ArrayList<Student> s2 = searchName(name, d);
			System.out.println("\nSTUDENTS WHOSE NAMES MATCH WITH " + name + " ARE:");
			System.out.println("==========================================");
			for (int i = 0; i < s2.size(); i++) {
				System.out.println("Name: " + s2.get(i).getName() + ", ID: " + 
						s2.get(i).getID());
			}
			break;
		case 3:
			System.out.print("Enter the age to be searched: ");
			int age = in.nextInt();
			int year = Student.THISYEAR - age;
			ArrayList<Student> s3 = searchYear(year, d);
			System.out.println("\nSTUDENTS WITH AGE " + age + " ARE:");
			System.out.println("=========================");
			for (int i = 0; i < s3.size(); i++) {
				System.out.println("Name: " + s3.get(i).getName() + ", ID: " + 
						s3.get(i).getID());
			}
			break;	
		case 4:
			System.out.print("Enter the course to be searched.\n" + 
					"<course dept> <course number> <credits>: ");
			String line = in.nextLine();
			String[] part = line.split(" ");
			Course c = new Course(part[0], Integer.parseInt(part[1])
					,Integer.parseInt(part[2])); 
			ArrayList<Student> s4 = searchCourse(c,d);
			System.out.println("\nSTUDENTS WITH COURSE " 
			+ part[0].toUpperCase() + " " + part[1] +  " FOR " + part[2] + " ARE:");
			System.out.println("======================================================");
			for (int i = 0; i < s4.size(); i++) {
				System.out.println("Name: " + s4.get(i).getName() + ", ID: " + 
						s4.get(i).getID());
			}
		break;
		} // end of switch case
		} catch (NoRecordForSearch e) {
			System.out.println("\nNO STUDENT RECORDS FOUND FOR THE GIVEN SEARCH!");
		}
	}
	
	/**
	 * search student id
	 * @param ID student id
	 * @param d current database
	 * @return student of given id
	 * @throws NoRecordForSearch 
	 */
	public static Student searchID(int ID, Database d) throws NoRecordForSearch {
		for (int i = 0; i < d.students.size(); i++){
			if (d.students.get(i).getID() == ID){
				return d.students.get(i);
			}
		}
		throw new NoRecordForSearch();
	}

	/**
	 * search student name
	 * @param name student name
	 * @param d current database
	 * @return array of students with given name
	 * @throws NoRecordForSearch 
	 */
	public static ArrayList<Student> searchName(String name, Database d) throws NoRecordForSearch 
			{
		ArrayList<Student> list = new ArrayList<Student>();
		for (int i = 0; i < d.students.size(); i++) {
			String n = d.students.get(i).getName();
			if (n.equalsIgnoreCase(name)) {
				list.add(d.students.get(i));
			} else {
				String[] anotherName = n.split(" ");
				for (int j = 0; j < anotherName.length; j++){
					if (anotherName[j].equalsIgnoreCase(name)){
						list.add(d.students.get(i));
						break;
					}
				}
			}
		}
		if (list.size() != 0) 
			return list;
		else throw new NoRecordForSearch();
	}


	/**
	 * search course
	 * @param c course
	 * @param d current database
	 * @return array of students taking given course
	 * @throws NoRecordForSearch 
	 */
	public static ArrayList<Student> searchCourse(Course c, Database  d) 
			throws NoRecordForSearch {
		ArrayList<Student> list = new ArrayList<Student>();
		for (int i = 0; i < d.students.size(); i++){
			for (int j = 0; j < d.students.get(i).getCourse().size();j++){
				if (c.equalsTo(d.students.get(i).getCourse().get(j))){
					list.add(d.students.get(i));
					break;
				}
			}
		}
		if (list.size() != 0) 
			return list;
		else throw new NoRecordForSearch();
	}

	/**
	 * search student who born in the given year
	 * @param year student birth year
	 * @param d current database
	 * @return array of students of given age
	 * @throws NoRecordForSearch 
	 */
	public static ArrayList<Student> searchYear(int year, Database d) throws NoRecordForSearch{
		ArrayList<Student> list = new ArrayList<Student>();
			for (int i = 0; i < d.students.size(); i++){
				if (d.students.get(i).getYear() == year){
					list.add(d.students.get(i));
				}
			}
			if (list.size() != 0) 
				return list;
			else throw new NoRecordForSearch();
	}


	/**
	 * delete students record
	 * @param ID the student's ID whom we want to delete
	 * @param d current database
	 * @throws NoRecordForSearch 
	 */
	public static void deleteStudents(int ID, Database d) throws NoRecordForSearch {
		// TODO Auto-generated method stub
		searchID(ID,d);
		for (int i = 0; i < d.students.size(); i++){
			if (ID == d.students.get(i).getID()){
				int removeID = d.students.get(i).getID();
				d.IDs.add(removeID);
				d.students.remove(i);
				break;
			}
		}
	}

	/**
	 * update student record 
	 * @param information record string
	 * @param d current database
	 * @throws CourseExistException 
	 * @throws NumberFormatException 
	 * @throws CourseNotExistException 
	 * @throws NoRecordForSearch 
	 * @return updated students lists
	 */
	public static ArrayList<Student> UpdateStudent(String information, Database d) 
			throws NumberFormatException, CourseExistException, CourseNotExistException, NoRecordForSearch {
		
		int thisStudent = -1;
		String part[] = information.split(",");
		for (int i = 0; i < d.students.size(); i++) {
			if (Integer.parseInt(part[0]) == d.students.get(i).getID()){
				thisStudent = i;
			}
		}
		if ( thisStudent == -1 ) {
			throw new NoRecordForSearch();
		}
		
		if (part[1].equals("name")){
				d.students.get(thisStudent).setName(part[2]);
				return d.students;
		} else if (part[1].equals("year")){
				d.students.get(thisStudent).setYear(Integer.parseInt(part[2]));
				return d.students;
		} else if (part[1].equals("courses")){
			if (part[2].equals("1")){
				for (int i = 3; i < part.length; i+=3){
					Course c = new Course(part[i], Integer.parseInt(part[i+1]),
							Integer.parseInt(part[i+2]));
					d.students.get(Integer.parseInt(part[0]) - 1).addCourse(c);
				} // end of for loop
				return d.students;
			}
			else if (part[2].equals("2")){
				boolean hasCourse = false;
				for (int i = 3; i < part.length; i+=3){
					Course c = new Course(part[i], Integer.parseInt(part[i+1]),Integer.parseInt(part[i+2]));
					for (int j = 0; j < d.students.get(Integer.parseInt(part[0]) - 1).getCourse().size();j++){
						if (d.students.get(Integer.parseInt(part[0]) - 1).getCourse().get(j).equalsTo(c)) {
							d.students.get(Integer.parseInt(part[0])-1).getCourse().remove(j);
							hasCourse = true;
						}
					}
				}
				if (hasCourse)
					return d.students;
				else throw new CourseNotExistException();
			}
		} // end of updating course
		return d.students;
	}

	/**
	 * print students' records in the database
	 * @param d current database
	 */
	public static void print(Database d) {
		// TODO Auto-generated method stub
		System.out.println("\nALL STUDENT RECORDS:\n====================");
		Collections.sort(d.students);
		for (int i = 0; i < d.students.size(); i++) {
			System.out.println(d.students.get(i).toString());
		}
		
	}

	/**
	 * save data to file
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void save(String filename, Database d) throws FileNotFoundException{
		
		PrintWriter writer = new PrintWriter(filename);
		for (int i = 0; i < d.students.size(); i++) {
			Student s = d.students.get(i);
			writer.print(s.getName() + "," + s.getYear());
			
			for (int j = 0; j < s.getCourse().size(); j++){
				Course c = s.getCourse().get(j);
				writer.print("," + c.getName() + "," + c.getNumber() + "," + c.getCredit());
			}
			writer.println();
		}
		writer.close();
	}	
	
	/**
	 * add a new student record in the database
	 * @param line a String of student record
	 */
	public static void addStudent(String line, Database d) {
		String part[] = line.split(",");
		Student a = new Student(part[0],Integer.parseInt(part[1]),Database.makeID(d));
		for (int i = 2; i < part.length; i += 3 ){
			Course c = new Course(part[i], Integer.parseInt(part[i+1])
					,Integer.parseInt(part[i+2])); 
			a.getCourse().add(c);
		}
		d.students.add(a);
		
	}
	
	/**
	 * check if recycled id available, otherwise return a new id
	 * @return a ID for added student 
	 */
	public static int makeID(Database d){
		if (!d.IDs.isEmpty()){
			return d.IDs.remove(d.IDs.size() - 1);
		}
		else{
			return countStudent += 1;
		}
	}
	
	/**
	 * readFile method: use filename input to scan all the items in an file and
	 * store them in the database.
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static Database readFile(String filename) throws FileNotFoundException {
		
		// create a new file
		File inputFile = new File(filename);
		
		// create a new array of students
		ArrayList<Student> s = new ArrayList<Student>();
		
		// create a new scanner
		Scanner inScan = new Scanner(inputFile);
		
		// store the content into array list
		countStudent = 0;
		while (inScan.hasNextLine()) {
			String Line = inScan.nextLine();
			String[] part = Line.split(",");
			countStudent++;
			Student a = new Student(part[0],Integer.parseInt(part[1]),countStudent);
			for (int i = 2; i < part.length; i += 3 ){
				Course c = new Course(part[i], Integer.parseInt(part[i+1])
						,Integer.parseInt(part[i+2])); 
				a.getCourse().add(c);
			}
			s.add(a);
			
		} // end of while loop
		Database d = new Database(s);
		inScan.close();
		return d;
	}
	
}
