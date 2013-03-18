
public class Table implements ITable {

	private Object elem[];    // values (each elem is a row)
	private int urm[];    // legaturile
	private int exist[];   // exista sau nu o inregistrare, 0 doesn`t exist
	private int cap;       // capacity
	
	public Table(int cap)
	{
		/*
		 * Table constructor
		 * Pre:
		 *     cap type Integer > 0
		 * Post:
		 *     elem[] vector of Objects (each elem is a row)
		 *     urm[] vector of int, each elem = cap initially
		 *     exist[] vector of int, each elem = 0 (no elem on that position initially)
		 */
		this.elem = new Object[cap];
		
		this.urm = new int[cap];
		for ( int i = 0; i < cap; i++ )
			urm[i] = cap;     
		
		this.exist = new int[cap];
		for ( int i = 0; i < cap; i++ )
			exist[i] = 0;     
		
		this.cap = cap;
	}
	
	
	public Object getValue(int index)
	{
		return this.elem[index];
	}
	
	public void setValue(int index, Object ob)
	{
		this.elem[index] = ob;
	}
	
	public int getNext(int index)
	{
		return this.urm[index];
	}
	
	public void setNext(int index, int value)
	{
		this.urm[index] = value;
	}
	
	public int getExist(int index)
	{
		return this.exist[index];
	}
	
	public void setExist(int index, int value)
	{
		this.exist[index] = value;
	}
	
	public int getCap()
	{
		return this.cap;
	}
	
	
	public boolean isEmpty()
	{
		/*
		 * Method that checks if the table has no elements or not
		 * Post:
		 * 	   returns "false" if table has >= 1 elem
		 * 	   returns "true" if table has = 0 elem
		 */
		for ( int i = 0; i < cap; i++ )
			if ( exist[i] == 1 )
				return false;
		return true;
	}
	
	
	public int noLines()
	{
		/*
		 * Method counts the number of lines in table
		 * Post:
		 * 	   return 0 if no elem, else the no of lines
		 */
		int nr = 0;
		if ( ! this.isEmpty() )
			for ( int i = 0; i < cap; i++ )
				nr += exist[i];         
		return nr;
	}
	
	
	public int addObject(Object ob)
	{
		/*
		 * Adds an Object to the end of the table
		 * Pre:
		 * 	  "ob" is of type Object for generics
		 * Post:
		 *    - if the table is full (all exist[] positions = 1) return 1, 
		 *    object not added
		 *    - if table not full, "ob" is appended to the table, link in
		 *    urm[] is made, return 0- success
		 */
		int i = 0;
		while ( ( i < cap ) && ( exist[i] == 1 ) )
			i++;                    // cautam o pozitie goala
		if ( i == cap)
		{
			System.out.println(" ~~~ FULL TABLE ! ~~~ ");
			return 1;
		}
		
		elem[i] = ob;                  // adaugam valoarea
		exist[i] = 1;               // exista
		int j = 0;
		while ( ( j < cap ) && ( urm[j] != -1 ) ) 
			j++;                    // cautam ultimul element din tabel
		if ( j == cap )
		{
			j = 0;
		}
		
		//System.out.println("i j =" + i + " " +j);
		urm[j] = i;                // setam legatura spre elem adaugat
		urm[i] = -1;               // elem adaugat va fi ultimul elem
		//for (int k=0; k<cap; k++)
		//	System.out.print("next["+k+"]="+urm[k]+"  ");
		return 0;
	}
	
	
	public int removeObject ( Object ob )
	{
		int i = 0;
		boolean continua = true;
		while ( continua ) 
		{
			while ( ( i < cap ) && (!(this.elem[i].equals(ob))) )
				i++;            
										// cautam pozitia pe care se afla obiectul
			if ( ( this.elem[i].equals(ob) ) && ( this.exist[i] == 0 ) && ( this.urm[i] == cap ) )
				continua = true;
			else
				continua = false;
		}
		if (  i == cap ) 
		{
			System.out.println(" !!! Elementul nu exista in tablou !!! ");
			return 1;                    // obiectul nu exista in tabel
		}
		
		int dr = this.urm[i];
		
		int j = 0;
		while ( ( j < this.noLines() ) && ( this.urm[j] != i ) )
			j++;               // cautam elementul anterior
		
		if ( j < this.noLines() )        // obiectul nu e primul el
		{
			int st = j;
			this.urm[st] = dr;          // setam legatura
		}
	                            // daca elementul pe care dorim sa il stergem e primul element
	
		this.exist[i] = 0;               // nu mai exista
		this.urm[i] = cap;              // si nici nu are legatura spre un element urmator
		return 0;
	}
	
	
	public boolean belongsTo(Object ob)
	{
		for ( int i = 0; i < this.noLines(); i++ ) {
			if ( this.elem[i].equals(ob) )
				return true;
		}	
		return false;
	}
	
	
	public int eraseTable()
	{
		int i;
		for ( i = 0; i < cap; i++ )
		{
			urm[i] = cap;          
			exist[i] = 0;
			elem[i] = null;
		}
		return 0;
	}
	
	
	public IIterator iterator()
	{
		return new Iterator(this);
	}
	
}
