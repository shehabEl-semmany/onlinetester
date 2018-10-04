package eg.edu.alexu.csd.filestructure.test.hash;

import eg.edu.alexu.csd.filestructure.hash.IHashQuadraticProbing;

public class QuadraticProbingSmokeTest extends AbstractHashSmokeTest{
    
    public static Class<?> getSpecifications(){
        return IHashQuadraticProbing.class;
    }

    @Override
    int[] getImplementationSpecificInformation(int testId) {
        switch(testId){
            case 1: return new int[] { 10642 };
            case 2: return new int[] { 113845 };
        }
        return null;
    }
}
