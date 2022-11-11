package model;

import java.util.HashMap;

public class dfsWPruning {

    public static int[][] results = new int[4][8];

    //THIS IS WHERE USER CAN SET ANY VARIABLE ORDERING
    public static String[] vars = {"A", "G", "B", "C", "D", "E", "F", "H"};

    public static int fails = 0;
    public static boolean searchBranch(HashMap<String, Integer> varMap, int index) {

        // Getting the values of the variables, so the constraints evaluate to a boolean
        int A = varMap.get("A");
        int B = varMap.get("B");
        int C = varMap.get("C");
        int D = varMap.get("D");
        int E = varMap.get("E");
        int F = varMap.get("F");
        int G = varMap.get("G");
        int H = varMap.get("H");

        //List of all constraints
        boolean[] constraints = {A > G, Math.abs(G-C) == 1, D >= G, D != C, E != C, E < D - 1, D != F-1, Math.abs(E-F) % 2 == 1, Math.abs(F-B) == 1, C != F, G != F, A <= H,  G < H, Math.abs(H-C) % 2 == 0, H != D, E != H-2, H != F};
        // Create a HashMap, where the integer is the index of the constraint, and the list is the variables that are involved in the constraint
        HashMap<Integer, String[]> varsPerConstraint = new HashMap<>();
        varsPerConstraint.put(0, new String[]{"A", "G"});
        varsPerConstraint.put(1, new String[]{"C", "G"});
        varsPerConstraint.put(2, new String[]{"D", "G"});
        varsPerConstraint.put(3, new String[]{"D", "C"});
        varsPerConstraint.put(4, new String[]{"C", "E"});
        varsPerConstraint.put(5, new String[]{"E", "D"});
        varsPerConstraint.put(6, new String[]{"F", "D"});
        varsPerConstraint.put(7, new String[]{"E", "F"});
        varsPerConstraint.put(8, new String[]{"B", "F"});
        varsPerConstraint.put(9, new String[]{"C", "F"});
        varsPerConstraint.put(10, new String[]{"F", "G"});
        varsPerConstraint.put(11, new String[]{"A", "H"});
        varsPerConstraint.put(12, new String[]{"H", "G"});
        varsPerConstraint.put(13, new String[]{"H", "C"});
        varsPerConstraint.put(14, new String[]{"H", "D"});
        varsPerConstraint.put(15, new String[]{"E", "H"});
        varsPerConstraint.put(16, new String[]{"F", "H"});

        for (int i = 0; i < constraints.length; i++) {
            String[] varsInConstraint = varsPerConstraint.get(i);
            // For each constraint, check if the variables involved have been assigned. if not, this constraint is skipped.
            if (varMap.get(varsInConstraint[0]) != 0 && varMap.get(varsInConstraint[1]) != 0) {
                boolean constraint = constraints[i];
                if (!constraint) {
                    // If constraint fails, then increment number of failed branches, and return false so the branch is not further explored.
                    fails++;
                    return false;
                }
            }
        }

        // If the index is 7 and the code gets here, that means all constraints passed. Print out this valid set of variables, and return true
        if (index == 7) {
            for (int j = 0; j < vars.length; j++) {
                System.out.println(vars[j] +"="+varMap.get(vars[j])+", ");
            }
            System.out.println();
            return true;
        }

        boolean result = false;
        //Increment index so the next entry in vars gets assigned in for loop
        index++;
        String buffer = "";

        //create buffer for tree
        for (int i = 0; i < index; i++) {
            buffer = buffer +  "          ";
        }
        for (int i = 1; i <= 4; i++) {
            //Assign next variable
            varMap.replace(vars[index], i);
            System.out.print(buffer);
            System.out.println(vars[index] + "=" + i);
            //Search the branch for this variable assignment
            boolean worked = searchBranch(varMap, index);
            //This logic is needed for the tree to print accurately
            System.out.println(buffer + "Solution?: " + worked);
            if (worked) {
                result = worked;
            }

            //Because varMap is a singular object, need to reset all the down-branch variables that were assigned
            //Resetting down-branch vars to 0, otherwise the constraint logic above won't work
            for (int j = index + 1; j < vars.length; j++) {
                varMap.replace(vars[j], 0);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        HashMap<String, Integer> varMap = new HashMap<>();


        for (int i = 1; i <= 4; i++) {
            //Initialize varMap to all be 0, the default value
            for (String curr : vars) {
                varMap.put(curr, 0);
            }

            // Initialize first variable in vars, and search that branch
            varMap.replace(vars[0], i);
            System.out.println(vars[0] + "=" + i);
            searchBranch(varMap, 0);
        }

        //Print number of failed consistency checks
        System.out.println(fails);

    }
}
