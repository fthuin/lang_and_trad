package regex;

import regex.Regex;
import errors.ParseError;

public class Main {
    public static void main(String[] args) throws ParseError {
        Regex regex = new Regex();
        String[][] str = {  {}, // true
                            {""}, // true
                            {"a"}, // false
                            {"", "b", "b"}, // false
                            {"", "b", "c"}, // true
                            {"", "b", "b", ""}, // false
                            {"b", "c"}, // true
                            {"a","b"}, // false
                            {"a","b","c"}, // true
                            {"a", "", "b", "", "c"}, // true
                            {"a", "", "b", "b", ""}, // false
                            {"a", "", "b", "", "c", ""}, // true
                            {"", "a","a","a","a","a","b", "", "c", ""}, // true
                            {"a","b","a","b","a","b","a","b","c"} // true
                            };
        for (String[] s : str){
            System.out.println(s + " " + regex.parse(s));
        }
    }
}
