/*************************************************************************************
 *
 * This class represents a fraction whose numerator and denominator are integers.
 *
 * Requirements:
 *      Implement interfaces: FractionInterface and Comparable (i.e. compareTo())
 *      Implement methods equals() and toString() from class Object
 *
 *      Should work for both positive and negative fractions
 *      Must always reduce fraction to lowest term 
 *      For numeratorber such as 3/-10, it is same as -3/10 (see hints 2. below)
 *      Must display negative fraction as -x/y,
 *         example: (-3)/10 or 3/(-10), must display as -3/10
 *      Must throw FractionException in case of errors, do not throw other types of exception objects
 *      Must not add new or modify existing data fields
 *      Must not add new public methods
 *      May add private methods
 *
 * Hints:
 *
 * 1. To reduce a fraction such as 4/8 to lowest terms, you need to divide both
 *    the numerator and the denominator by their greatest common denominator.
 *    The greatest common denominator of 4 and 8 is 4, so when you divide
 *    the numerator and denominator of 4/8 by 4, you get the fraction 1/2.
 *    The recursive algorithm which finds the greatest common denominator of
 *    two positive integers is implemnted (see code)
 *       
 * 2. It will be easier to determine the correct sign of a fraction if you force
 *    the fraction's denominator to be positive. However, your implementation must 
 *    handle negative denominators that the client might provide.
 *           
 * 3. You need to downcast reference parameter FractionInterface to Fraction if  
 *    you want to use it as Fraction. See add, subtract, multiply and divide methods
 *
 * 4. Use "this" to access this object if it is needed
 *
 ************************************************************************************/

package PJ1;

public class Fraction implements FractionInterface, Comparable<Fraction>
{
	private	int numerator;	
	private	int denominator;	

	public Fraction()
	{
        this.numerator = 0;
        this.denominator = 1;
		// set fraction to default = 0/1
	}	// end default constructor

	public Fraction(int num, int den)
	{
        this.numerator = num;
        this.denominator = den;

        if(this.denominator == 0) {
            throw new FractionException();
        }
		// implement this method!
	}	// end constructor

	public void setFraction(int num, int den)
	{
		// implement this method!
		if (den == 0)
           throw new FractionException();

        this.numerator = num;
        this.denominator = den;

		// return FractionException if initial Denominator is 0
	}	// end setFraction

	public double toDouble()
	{
		// return double floating point value
		// implement this method!
		return (double)this.numerator/this.denominator;
	}	// end toDouble 

	public FractionInterface add(FractionInterface aFraction)
	{
        Fraction newFraction = (Fraction)aFraction;

        Fraction result = new Fraction(
                this.numerator*newFraction.denominator + this.denominator*newFraction.numerator,
                this.denominator*newFraction.denominator
        );

        result.reduceFractionToLowestTerms();
        result = handleNegative(result);
        return result;
                // return a new Fraction object
                // a/b + c/d is (ad + cb)/(bd)
                // implement this method!
	}	// end add

	public FractionInterface subtract(FractionInterface aFraction)
	{
        Fraction newFraction = (Fraction)aFraction;

        Fraction result = new Fraction(
                this.numerator*newFraction.denominator - this.denominator*newFraction.numerator,
                this.denominator*newFraction.denominator);

        result.reduceFractionToLowestTerms();
        result = handleNegative(result);
                // return a new Fraction object
                // a/b - c/d is (ad - cb)/(bd)
                // implement this method!
         return result;
	}	// end subtract

	public FractionInterface multiply(FractionInterface aFraction)
	{
        Fraction newFraction = (Fraction)aFraction;
        Fraction result = new Fraction(
                this.numerator* newFraction.numerator, this.denominator*newFraction.denominator);

        result.reduceFractionToLowestTerms();
        result = handleNegative(result);
                // return a new Fraction object
                // a/b * c/d is (ac)/(bd)
                // implement this method!
         return result;
	}	// end multiply

	public FractionInterface divide(FractionInterface aFraction)
	{
        Fraction newFraction = (Fraction)aFraction;
        Fraction result;
        if(newFraction.denominator == 0 || newFraction.numerator == 0) {
            throw new FractionException();
        }  else {
            result = new Fraction(this.numerator*newFraction.denominator,this.denominator*newFraction.numerator);
        }

        result.reduceFractionToLowestTerms();
        result = handleNegative(result);
                // return a new Fraction object
                // return FractionException if aFraction is 0
                // a/b / c/d is (ad)/(bc)
                // implement this method!
                return result;
	}	// end divide

	public FractionInterface getReciprocal() {

        Fraction newFraction;
        if (numerator == 0) {
            throw new FractionException();
        } else {
            newFraction = new Fraction(denominator, numerator);
        }
                // return a new Fraction object
                // return FractionException if new Fraction is 0
                // implement this method!
        return newFraction;
	} // end getReciprocal


	public boolean equals(Object other)
	{
        Fraction newFraction = ((Fraction)other);


        return (this.toDouble() == newFraction.toDouble());

	} // end equals


	public int compareTo(Fraction other)
	{
        if(this.toDouble() < other.toDouble()) {
            return -1;
        }else if(this.toDouble() == other.toDouble()){
            return 0;
        }

        return 1;
                // implement this method!
	} // end compareTo

	
	public String toString()
	{
		return numerator + "/" + denominator;
	} // end toString


	/** Task: Reduces a fraction to lowest terms. */

        //-----------------------------------------------------------------
        //  private methods start here
        //-----------------------------------------------------------------

	private void reduceFractionToLowestTerms()
	{
                // implement this method!
                //
                // Outline:
                // compute GCD of numerator & denominator
                // GCD works for + numeratorbers.
                // So, you should eliminate - sign
                // then reduce numeratorbers : numerator/GCD and denominator/GCD
		
		int gcd = GCD(Math.abs(numerator), Math.abs(denominator));

		numerator = numerator / gcd;
		denominator = denominator / gcd;
	}	// end reduceFractionToLowestTerms

  	/** Task: Computes the greatest common divisor of two integers.
	 *  @param integerOne	 an integer
	 *  @param integerTwo	 another integer
	 *  @return the greatest common divisor of the two integers */
	private int GCD(int integerOne, int integerTwo)
	{
		 int result;

		 if (integerOne % integerTwo == 0)
			result = integerTwo;
		 else
			result = GCD(integerTwo, integerOne % integerTwo);

		 return result;
	}	// end GCD

    /** Task : Handle negative values in either denominator and numerator
     * @param aFraction
     * @return the new Fraction value.
     */
    private Fraction handleNegative ( Fraction aFraction){
        if(aFraction.toDouble() < 0)  {
            if(aFraction.denominator < 0)
                aFraction = new Fraction(aFraction.numerator* (-1) , Math.abs(aFraction.denominator) );
        }else {
            if(aFraction.numerator < 0 && aFraction.denominator < 0)
                aFraction = new Fraction(Math.abs(aFraction.numerator), Math.abs(aFraction.denominator));
        }

        return aFraction;
    }
	//-----------------------------------------------------------------
	//  Simple test is provided here 

	public static void main(String[] args)
	{
		FractionInterface firstOperand = null;
		FractionInterface secondOperand = null;
		FractionInterface result = null;
                double doubleResult = 0.0;

		Fraction nineSixteenths = new Fraction(9, 16);	// 9/16
		Fraction oneFourth = new Fraction(1, 4);        // 1/4

		System.out.println("\n=========================================\n");
		// 7/8 + 9/16
		firstOperand = new Fraction(7, 8);
		result = firstOperand.add(nineSixteenths);
		System.out.println("The sum of " + firstOperand + " and " +
				nineSixteenths + " is \t\t" + result);
		System.out.println("\tExpected result :\t\t23/16\n");

		// 9/16 - 7/8
		firstOperand = nineSixteenths;
		secondOperand = new Fraction(7, 8);
		result = firstOperand.subtract(secondOperand);
		System.out.println("The difference of " + firstOperand	+
				" and " +	secondOperand + " is \t" + result);
		System.out.println("\tExpected result :\t\t-5/16\n");


		// 15/-2 * 1/4
		firstOperand = new Fraction(15, -2); 
		result = firstOperand.multiply(oneFourth);
		System.out.println("The product of " + firstOperand	+
				" and " +	oneFourth + " is \t" + result);
		System.out.println("\tExpected result :\t\t-15/8\n");

		// (-21/2) / (3/7)
		firstOperand = new Fraction(-21, 2); 
		secondOperand= new Fraction(3, 7); 
		result = firstOperand.divide(secondOperand);
		System.out.println("The quotient of " + firstOperand	+
				" and " +	secondOperand + " is \t" + result);
		System.out.println("\tExpected result :\t\t-49/2\n");

		// -21/2 + 7/8
		firstOperand = new Fraction(-21, 2); 
		secondOperand= new Fraction(7, 8); 
		result = firstOperand.add(secondOperand);
		System.out.println("The sum of " + firstOperand	+
				" and " +	secondOperand + " is \t\t" + result);
		System.out.println("\tExpected result :\t\t-77/8\n");


                // 0/10, 5/(-15), (-22)/7
		firstOperand = new Fraction(0, 10); 
                doubleResult = firstOperand.toDouble();
		System.out.println("The double floating point value of " + firstOperand	+ " is \t" + doubleResult);
		System.out.println("\tExpected result \t\t\t0.0\n");
		firstOperand = new Fraction(1, -3); 
                doubleResult = firstOperand.toDouble();
		System.out.println("The double floating point value of " + firstOperand	+ " is \t" + doubleResult);
		System.out.println("\tExpected result \t\t\t-0.333333333...\n");
		firstOperand = new Fraction(-22, 7); 
                doubleResult = firstOperand.toDouble();
		System.out.println("The double floating point value of " + firstOperand	+ " is \t" + doubleResult);
		System.out.println("\tExpected result \t\t\t-3.142857142857143");
		System.out.println("\n=========================================\n");
		firstOperand = new Fraction(-21, 2); 
		System.out.println("First = " + firstOperand);
		// equality check
		System.out.println("check First equals First: ");
		if (firstOperand.equals(firstOperand))
			System.out.println("Identity of fractions OK");
		else
			System.out.println("ERROR in identity of fractions");

		secondOperand = new Fraction(-42, 4); 
		System.out.println("\nSecond = " + secondOperand);
		System.out.println("check First equals Second: ");
		if (firstOperand.equals(secondOperand))
			System.out.println("Equality of fractions OK");
		else
			System.out.println("ERROR in equality of fractions");

		// comparison check
		Fraction first  = (Fraction)firstOperand;
		Fraction second = (Fraction)secondOperand;
		
		System.out.println("\ncheck First compareTo Second: ");
		if (first.compareTo(second) == 0)
			System.out.println("Fractions == operator OK");
		else
			System.out.println("ERROR in fractions == operator");

		second = new Fraction(7, 8);
		System.out.println("\nSecond = " + second);
		System.out.println("check First compareTo Second: ");
		if (first.compareTo(second) < 0)
			System.out.println("Fractions < operator OK");
		else
			System.out.println("ERROR in fractions < operator");

		System.out.println("\ncheck Second compareTo First: ");
		if (second.compareTo(first) > 0)
			System.out.println("Fractions > operator OK");
		else
			System.out.println("ERROR in fractions > operator");

		System.out.println("\n=========================================");

		System.out.println("\ncheck FractionException: 1/0");
		try {
			Fraction a1 = new Fraction(1, 0);		    
		        System.out.println("Error! No FractionException");
		}
		catch ( FractionException fe )
           	{
              		System.err.printf( "Exception: %s\n", fe );
           	} // end catch
		System.out.println("Expected result : FractionException!\n");

		System.out.println("\ncheck FractionException: division");
		try {
			Fraction a2 = new Fraction();		    
			Fraction a3 = new Fraction(1, 2);		    
			a3.divide(a2);
		        System.out.println("Error! No FractionException");
		}
		catch ( FractionException fe )
           	{
              		System.err.printf( "Exception: %s\n", fe );
           	} // end catch
		System.out.println("Expected result : FractionException!\n");



	}	// end main
} // end Fraction

