package test;

import static org.junit.Assert.fail;

import org.junit.Test;

public class DieTest {

	model.Die die = new model.Die();

	/**
	 * testRoll() - Tester om roll metoden fungerer. 
	 */
	@Test
	public void testRoll() {
		int countd1=0, countd2=0, countd3=0, countd4=0, countd5=0, countd6=0;

		for (int i = 0; i<100000; i++) {
            die.roll();
            switch(die.getEyes()) {
            case 1: countd1++;
            break;
            case 2: countd2++;
            break;
            case 3: countd3++;
            break;
            case 4: countd4++;
            break;
            case 5: countd5++;
            break;
            case 6: countd6++;
            break;
            default: fail("Virker ikke");
            break;
            }
		}
        // Vi tester om terning er cirka lige hyppigt med en afvigelse på 4%
        int test = (int)(100000*16.6667)/(100); //1/6 af størrelsen på testen
        int borderUp = (int)(test + (test*4)/(100));
        int borderDown = (int)(test - (test*4)/(100));
        
        //Dump programmet hvis hvert tilfælde er ude for den øvre og nedre grænse
        if (countd1 > borderUp || countd1 < borderDown || countd2 > borderUp || countd2 < borderDown || 
        		countd3 > borderUp || countd3 < borderDown || countd4 > borderUp || countd4 < borderDown || 
        		countd5 > borderUp || countd5 < borderDown || countd6 > borderUp || countd6 < borderDown){
            fail("Hyppigheden af de forskellige forekommer ikke lige ofte");   

	}
	}
}

