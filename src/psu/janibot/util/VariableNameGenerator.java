package psu.janibot.util;

import java.util.ArrayList;
import java.util.Random;

public class VariableNameGenerator {

    private ArrayList<String> variable;

    public VariableNameGenerator() {
        variable = new ArrayList<>();
    }

    public String generate(){

        Random random = new Random();
        String name = "";

        if(variable.isEmpty()){
            name = "var" + Math.abs(random.nextInt());
            variable.add(name);
            return name;
        }else {
            while (check(name)) {
                name = "var" + Math.abs(random.nextInt());
            }
            return name;
        }
    }

    private boolean check(String name) {
        for (String s: variable){
            if(name.equals(s)){
                return true;
            }
        }
        variable.add(name);
        return false;
    }

    public void reset(){
        variable.clear();
    }
}