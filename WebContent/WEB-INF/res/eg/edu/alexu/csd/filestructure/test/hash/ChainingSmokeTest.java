package eg.edu.alexu.csd.filestructure.test.hash;

import eg.edu.alexu.csd.filestructure.hash.IHashChaining;

public class ChainingSmokeTest extends AbstractHashSmokeTest{
    
    public static Class<?> getSpecifications(){
        return IHashChaining.class;
    }

    @Override
    int[] getImplementationSpecificInformation(int testId) {
        switch(testId){
            case 1: return new int[] { 5760 };
            case 2: return new int[] { 620000 };
        }
        return null;
    }

}
