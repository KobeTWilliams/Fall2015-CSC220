/* This file specifies methods for FractionInterface		*/
/* Do not modify this file!!                  			*/

package PJ1;

public interface FractionInterface 
{
        /** Task: Sets a fraction to a given value.
         *  @param num is the integer numerator
         *  @param den is the integer denominator
         *  @throws ArithmeticException if denominator is 0  */
        public void setFraction(int num, int den);

	/** Task: convert a fraction to double value
	 *  @return the double floating point value of a fraction */
	public double toDouble();

	/** Task: Adds two fractions.
	 *  @param aFraction is a fraction that is the second operand of the addition
	 *  @return a fraction which is the sum of the invoking fraction and the aFraction */
	public FractionInterface add(FractionInterface aFraction);

	/** Task: Subtracts two fractions.
	 *  @param aFraction a fraction that is the second operand of the subtraction
	 *  @return a fraction which is the difference of the invoking fraction and the second operand */
	public FractionInterface subtract(FractionInterface aFraction);

	/** Task: Multiplies two fractions.
	 *  @param aFraction a fraction that is the second operand of the multiplication
	 *  @return a fraction which is the product of the invoking fraction and the aFraction*/
	public FractionInterface multiply(FractionInterface aFraction);

	/** Task: Divides two fractions.
	 *  @param aFraction a fraction that is the second operand of the division
	 *  @return a fraction which the quotient of the invoking fraction and the aFraction
         *  @throws FractionException if aFraction is 0 */
	public FractionInterface divide(FractionInterface aFraction);

	/** Task: Get's the fraction's reciprocal
	 *  @return the reciprocal of the invoking fraction 
         *  @throws FractionException if the new number with denominator is 0*/
	public FractionInterface getReciprocal();

}
