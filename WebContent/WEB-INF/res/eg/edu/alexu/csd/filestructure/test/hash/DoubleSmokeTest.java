package eg.edu.alexu.csd.filestructure.test.hash;

import eg.edu.alexu.csd.filestructure.hash.IHashDouble;

public class DoubleSmokeTest extends AbstractHashSmokeTest{
    
    public static Class<?> getSpecifications(){
        return IHashDouble.class;
    }

    @Override
    int[] getImplementationSpecificInformation(int testId) {
        switch(testId){
            case 1: return new int[] { 18017 };
            case 2: return new int[] { 152444 };
        }
        return null;
    }
}
