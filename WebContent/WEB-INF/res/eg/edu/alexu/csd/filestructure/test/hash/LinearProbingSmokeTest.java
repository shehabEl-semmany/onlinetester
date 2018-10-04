package eg.edu.alexu.csd.filestructure.test.hash;

import eg.edu.alexu.csd.filestructure.hash.IHashLinearProbing;

public class LinearProbingSmokeTest extends AbstractHashSmokeTest{
    
    public static Class<?> getSpecifications(){
        return IHashLinearProbing.class;
    }

    @Override
    int[] getImplementationSpecificInformation(int testId) {
        switch(testId){
            case 1: return new int[] { 6680 };
            case 2: return new int[] { 203684 };
        }
        return null;
    }
}
