
class CourseExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7786995692946779981L;

	public CourseExistException() {
        super();
    }
}	
	
class CourseNotExistException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8948742406214877622L;

		public CourseNotExistException() {
	        super();
	    }
	}

class IncorrectOptionForSearch extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectOptionForSearch() {
        super();
    }
}

class NoRecordForSearch extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3953688350951097617L;

	public NoRecordForSearch() {
        super();
    }
}