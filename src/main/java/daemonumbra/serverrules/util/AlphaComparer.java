package daemonumbra.serverrules.util;

import java.text.Collator;

/**
 * A wrapper for Collator
 */
public class AlphaComparer {
    public static int Compare(Object firstObject, Object secondObject){

        if(!firstObject.getClass().isAssignableFrom(String.class)){
            throw new ClassCastException(firstObject.toString() + " is not a String");
        }else if(!secondObject.getClass().isAssignableFrom((String.class))){
            throw new ClassCastException(secondObject.toString() + " is not a string");
        }
        //int max;
        //int longest;
        String first = (String)firstObject;
        String second = (String)secondObject;

        Collator collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);
        int compValue = collator.compare(first,second);
        return (compValue)/Math.abs(compValue);

//        char[] firstChars = first.toCharArray();
//        char[] secondChars = second.toCharArray();
//
//        if(firstObject == secondObject){
//            return 0;
//        }
//        if(firstChars.length < secondChars.length){
//            max = firstChars.length;
//            longest = 1;
//        }else{
//            max = secondChars.length;
//            longest = -1;
//        }
//
//        for(int i = 0;i<max;i++){
//            if(firstChars[i] < secondChars[i]){
//                return -1;
//            }else if(firstChars[i] > secondChars[i]){
//                return 1;
//            }
//        }
//        return longest;
    }
}
