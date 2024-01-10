// two file functions imported into the program
import java.io.FileNotFoundException;
import java.io.PrintStream;
// scanner function (used to record user input) imported into the program
import java.util.Scanner;
public class Main {
    // method to calculate the total sum of deductibles eligible by the user
    public static int deductibleMethod(String string, int annualIncome){
        int deductiblesTotal = 0;
        Scanner questionScanner = new Scanner(System.in);
        System.out.println(string);
        int userInput = 0;
        try{
            userInput = questionScanner.nextInt();
        }
        catch (Exception e){
            System.out.println("Oops! You have typed something other than an integer value.");
        }
        if(userInput == 0){
            System.out.println("No money is contributed to this deductible.");
            return 0;
        }
        else if(string.contains("expenses")){
            if(userInput > annualIncome){
                System.out.println("You have no taxes to pay this year.");
                System.exit(0);
            }
            else{
                System.out.println("Thank you! "+userInput+" will be added as part of your deductibles.");
                deductiblesTotal = deductiblesTotal + userInput;
            }
        }
        else if(string.contains("self-education") || string.contains("home-loan") || string.contains("retirement")){
            if(userInput > 100000){
                System.out.println("You cannot exceed 100,000 for this deductible. Therefore the amount deducted for this entity will cap at 100,000.");
                deductiblesTotal = deductiblesTotal + 100000;
            }
            else{
                System.out.println("Thank you! "+userInput+" will be added as part of your deductibles.");
                deductiblesTotal = deductiblesTotal + userInput;
            }
        }
        else if(string.contains("charitable")){
            if(userInput > annualIncome * 0.35) {
                double thirtyFivePercent = annualIncome * 0.35;
                int thirtyFivePercentInt = (int) thirtyFivePercent;
                System.out.println("You cannot exceed 35% of your salary for charity donations. Therefore, the amount added as part of your deductibles will be capped at " + thirtyFivePercentInt + ".");
                deductiblesTotal = deductiblesTotal + thirtyFivePercentInt;
            }
            else{
                System.out.println("Thank you! "+userInput+" will be added as part of your deductibles.");
                deductiblesTotal = deductiblesTotal + userInput;
            }
        }
        return deductiblesTotal;
    }
    // method to see if user is eligible for an allowance
    public static boolean allowanceCheck(String string){
        boolean trueOrFalse = false;
        Scanner questionScanner = new Scanner(System.in);
        System.out.println(string);
        String userInput = questionScanner.nextLine().toLowerCase();
        if(userInput.contains("yes") || userInput.contains("no")){
            if(userInput.equals("yes")){
                return true;
            }
            return false;
        }
        System.out.println("You have inputted neither 'yes' or 'no'. The system translates this as 'no' by default.");
        return false;
    }
    // method to calculate the total amount of allowances
    public static int allowanceTotal(boolean isDisabled, boolean isSingleParent, boolean isDependentOver60, boolean isDependentUnder60){
        Scanner userInput = new Scanner(System.in);
        int allowance = 132000;
        if(isDisabled) allowance = allowance + 75000;
        if(isSingleParent) allowance = allowance + 132000;
        if(isDependentOver60){
            System.out.println("You have previously stated that you have an elderly over 60/under 60 but disabled with you.");
            System.out.println("Please type the number of grandparents under this category that reside with you in your home. Type '0' if you don't have any.");
            int resideOver60 = userInput.nextInt()*100000;
            System.out.println("Please type the number of grandparents under this category but don't reside with you in your home. Type '0' if you don't have any.");
            int noResideOver60 = userInput.nextInt()*50000;
            allowance = allowance + resideOver60 + noResideOver60;
        }
        if(isDependentUnder60){
            System.out.println("You have previously stated that you have an elderly aged between 50-59 with you.");
            System.out.println("Please type the number of grandparents under this category that reside with you in your home. Type '0' if you don't have any.");
            int resideUnder60 = userInput.nextInt()*50000;
            System.out.println("Please type the number of grandparents under this category but don't reside with you in your home. Type '0' if you don't have any.");
            int noResideUnder60 = userInput.nextInt()*25000;
            allowance = allowance + resideUnder60 + noResideUnder60;
        }
        return allowance;
    }
    // checks what tax bracket the user qualifies into and calculates the amount of tax accordingly
    public static double taxBracket(int netChargeableIncome){
        double tax = 0;
        if(netChargeableIncome <= 50000){
            tax = tax + netChargeableIncome * 0.02;
        }
        else if(netChargeableIncome > 50000 && netChargeableIncome <= 100000){
            tax = tax + 50000*0.02;
            tax = tax + (netChargeableIncome - 50000)*0.06;
        }
        else if(netChargeableIncome > 100000 && netChargeableIncome <= 150000){
            tax = tax + 50000*0.02;
            tax = tax + 50000*0.06;
            tax = tax + (netChargeableIncome-100000)*0.1;
        }
        else if(netChargeableIncome > 150000 && netChargeableIncome <= 200000){
            tax = tax + 50000*0.02;
            tax = tax + 50000*0.06;
            tax = tax + 50000*0.1;
            tax = tax + (netChargeableIncome-150000)*0.14;
        }
        else if(netChargeableIncome > 200000){
            tax = tax + 50000*0.02;
            tax = tax + 50000*0.06;
            tax = tax + 50000*0.1;
            tax = tax + 50000*0.14;
            tax = tax + (netChargeableIncome-200000) * 0.17;
        }
        return tax;
    }
    // method to check whether user wants to pay in instalments and prints result to the user
    public static int instalments(double tax){
        System.out.println("Would you like to pay your taxes in instalments?");
        Scanner userInput = new Scanner(System.in);
        String yesOrNo = userInput.nextLine().toLowerCase();
        if(yesOrNo.contains("yes") || yesOrNo.contains("no")){
            if(yesOrNo.equals("yes")){
                System.out.println("How many instalments would you like to pay your taxes in this year?");
                int instalmentNumber = userInput.nextInt();
                System.out.println("For this year, you will have to pay a total of $"+Math.round(tax)+" this year for your taxes. In instalments, this translates to "+Math.round(tax/instalmentNumber)+", divided into "+instalmentNumber+" instalments.");
                return instalmentNumber;
            }
            else{
                System.out.println("For this year, you will have to pay a total of $"+Math.round(tax)+" this year for your taxes, due in one payment only.");
                return 0;
            }
        }
        System.out.println("You have typed something other than a 'yes' or 'no', the system takes this as a 'no' by default.");
        System.out.println("For this year, you will have to pay a total of $"+Math.round(tax)+" this year for your taxes, due in one payment only.");
        return 0;
    }
    public static void main(String[] args) throws FileNotFoundException {
        // welcome message
        System.out.println("Welcome to the Progressive Tax Calculator! This salary tax calculator is based on Hong Kong's progressive taxation policies in year 2023-24, and is suitable for people with single/separated/widowed/divorced marital status. In this calculator we have included 5 existing deductibles and 5 allowances that could reduce your net chargeable/taxable income! The information from the program are for reference only and is not the full representation of Hong Kong's taxation system.");
        System.out.println("---");
        Scanner annualIncomeScanner = new Scanner(System.in);
        // stores annual income before deductibles and allowance elimination
        System.out.println("What is your annual income for this year (including bonuses)?");
        int annualIncome = 0;
        try{
            annualIncome = annualIncomeScanner.nextInt();
        }
        catch (Exception e){
            System.out.println("You have typed something other than an integer for your income. Due to this, the system defauls your annual income as 0 and therefore you have no taxes to pay. Please restart the program if this was a mistype.");
            System.exit(0);
        }
        if(annualIncome <= 132000){
            System.out.println("You have no taxes to pay this year.");
            System.exit(0);
        }
        // asks 5 deductibles questions to receive user input and constantly adds to the deductiblesTotal amount in main function
        int deductiblesTotal = 0;
        deductiblesTotal = deductiblesTotal + deductibleMethod("How much are your outgoings and expenses this year?", annualIncome);
        deductiblesTotal = deductiblesTotal + deductibleMethod("What are your expenses to self-education? If none type '0'.", annualIncome);
        deductiblesTotal = deductiblesTotal + deductibleMethod("What is the total annual interest paid for your home-loan? If none type '0'.", annualIncome);
        deductiblesTotal = deductiblesTotal + deductibleMethod("What is the amount of your annual charitable donations? If none type '0'.", annualIncome);
        deductiblesTotal = deductiblesTotal + deductibleMethod("How much did you pay this year to mandatory retirement schemes?", annualIncome);
        // calls on allowanceCheck method to see if user is eligible for certain allowances
        boolean isDisabled = allowanceCheck("Are you physically disabled? Please type 'yes' if you qualify for the personal disability allowance.");
        boolean isSingleParent = allowanceCheck("Are you a single parent? Please type 'yes' if you qualify for the single parent allowance.");
        boolean isDependentOver60 = allowanceCheck("Please type 'yes' if you are currently looking after grandparent(s) aged 60 or over, or a disabled grandparent(s) under 60 which qualifies you for the specific allowance.");
        boolean isDependentUnder60 = allowanceCheck("Please type 'yes' if you have grandparent(s) who is/are between the age of 50-59 which qualifies you for the specific allowance.");
        // calls on allowancesTotal method to see the total sum of allowances
        int allowancesTotal = allowanceTotal(isDisabled, isSingleParent, isDependentOver60, isDependentUnder60);
        // calculates taxable income from sum of deductibles and allowances deducted from annual income
        int netChargeableIncome = annualIncome - deductiblesTotal - allowancesTotal;
        // checks if there is any taxable income left and if there is none the program ends
        if(netChargeableIncome < 0){
            System.out.println("You have no taxes to pay this year.");
            System.exit(0);
        }
        // calls on taxBracket function to calculate the final amount of tax charged
        double taxCharged = taxBracket(netChargeableIncome);
        // calls on instalments function to check whether the user wants result printed in instalments
        int numOfInstalments = instalments(taxCharged);
        // asks whether user wants to store the data into a file
        Scanner fileMove = new Scanner(System.in);
        System.out.println("Would you like to store your tax amount into a file?");
        String userFile = fileMove.nextLine().toLowerCase();
        if(userFile.contains("yes") || userFile.contains("no")){
            if(userFile.toLowerCase().equals("yes")){
                PrintStream fileEdit = new PrintStream("UserData.kt");
                fileEdit.println(taxCharged + ", paid in "+numOfInstalments+" instalments.");
            }
        }
        else{
            System.out.println("You have typed something other than a 'yes' or 'no'. The system takes your response as a 'no' by default.");
        }
        // end message
        System.out.println("That is the end of the tax calculator. Thank you for using the application!");
    }
}