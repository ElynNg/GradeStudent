import java.util.Scanner;

public class GradeStudent {
	public static void main(String[] args) {
		begin();
		double[] midScore = new double[2];// 0-weight, 1-weightedScore
		double[] finalScore = new double[2];// 0-weight, 1-weightedScore
		double[] homeworkScore = new double[2];// 0-weight, 1-weightedScore

		midScore = midTerm();
		finalScore = finalTerm(midScore[0]);
		homeworkScore = homework(midScore[0], finalScore[0]);
		report(midScore[1], finalScore[1], homeworkScore[1]);
	}

	public static void begin() { //display
		System.out.println("This program reads exam/homework scores and reports your overall course grade");
	}

	public static double[] midTerm() { //process + display result
		System.out.println("Middle Term:");
		double[] midScore = { 0, 0 };// 0 - weight, 1 - weighted score
		midScore[0] = typeWeight();//enter weight score
		if (midScore[0] == 0) { // weight = 0 -> skip mid-term score
			return midScore;
		}
		int scoreEarned = typeScoreEarned();//enter score
		int scoreShifted = typeScoreShifted();//bonus 1 = yes, 2 = no
		int totalPoint = 0;
		if (scoreShifted == 2) {
			totalPoint = scoreEarned; //bonus = 0 => totalPoint = score;
		} else {
			System.out.print("Shift Amount? ");
			Scanner input = new Scanner(System.in);
			int shiftAmount = input.nextInt();
			totalPoint = totalPointCal(scoreEarned, shiftAmount);//calculate - check totalScore condition
		}
		System.out.println("Total points = " + totalPoint + " / 100");
		midScore[1] = weightedScoreCal(totalPoint, midScore[0], 100);
		System.out.printf("Weighted score = %.1f / %.0f", midScore[1], midScore[0]);
		System.out.println();
		return midScore;
	}

	public static double[] finalTerm(double midWeight) {
		Scanner input = new Scanner(System.in);
		double[] finalScore = { 0, 0 };// 0 - weight, 1 - weighted score
		System.out.println("Final Term:");
		finalScore[0] = typeWeight();
		while (finalScore[0] > (100 - midWeight)) {//totalWeight can only be 100
			System.out.println("The total weight must equals to 100. Please enter the correct weight");
			finalScore[0] = typeWeight();
		}
		if (finalScore[0] == 0) {
		}
		int scoreEarned = typeScoreEarned();
		int scoreShifted = typeScoreShifted();
		int totalPoint = 0;
		if (scoreShifted == 2) {
			totalPoint = scoreEarned;
		} else {
			System.out.print("Shift Amount? ");
			int shiftAmount = input.nextInt();
			totalPoint = totalPointCal(scoreEarned, shiftAmount);
		}
		System.out.println("Total points = " + totalPoint + " / 100");
		finalScore[1] = weightedScoreCal(totalPoint, finalScore[0], 100);
		System.out.printf("Weighted score = %.1f / %.0f", finalScore[1], finalScore[0]);
		System.out.println();
		return finalScore;
	}

	public static double[] homework(double midWeight, double finalWeight) {
		Scanner input = new Scanner(System.in);
		double homeworkScore[] = { 0, 0 };
		int maxPoint = 0;
		int totalPoint = 0;
		System.out.println("Homework: ");
		homeworkScore[0] = typeWeight();
		while (homeworkScore[0] != (100 - (midWeight + finalWeight))) {
			System.out.println("The total weight must equals to 100. Please enter the correct weight");
			homeworkScore[0] = typeWeight();
		}
		if (homeworkScore[0] == 0) {
			return homeworkScore;
		}
		int numOfAsm = typeNumOfAsm();
		if (numOfAsm != 0) {//skip if there is no asm
			int[] asmScore = new int[numOfAsm + 1];
			typeAsmPoint(numOfAsm, asmScore);//score of each asm
			System.out.print("How many section did you attend? ");
			int section = input.nextInt() * 5;//attendance point
			if (section > 30) {// attendancePoint > 30 -> = 30
				section = 30;
			}
			System.out.println("Section Point = " + section + " / 30");
			for (int i = 1; i <= numOfAsm; i++) {
				totalPoint += asmScore[i];
			}
			totalPoint += section;//totalPoint = asmPoint + attendancePoint
			maxPoint = asmScore[0] + 30;//maxPoint = max-asmPoint + max-attendancePoint
		}

		System.out.println("Total Point = " + totalPoint + " / " + maxPoint);
		homeworkScore[1] = weightedScoreCal(totalPoint, homeworkScore[0], maxPoint);//
		System.out.printf("Weighted score = %.1f / %.0f", homeworkScore[1], homeworkScore[0]);
		System.out.println();
		return homeworkScore;
	}

	public static void report(double midWeight, double finalWeight, double homeworkWeight) {
		System.out.printf("Overall percentage = %.1f\n", (midWeight + finalWeight + homeworkWeight));
		String GPA = gpaCal(Math.round(midWeight + finalWeight + homeworkWeight));
		System.out.println("Your grade will be at least: " + GPA);
	}

	public static double typeWeight() {
		Scanner input = new Scanner(System.in);
		System.out.print("Weight (0-100)? ");
		double weight = input.nextDouble();
		while (weight < 0 || weight > 100) {//0 < weight < 100
			System.out.println("Please enter the correct weight");
			weight = input.nextDouble();
		}
		return weight;
	}

	public static int typeScoreEarned() {
		Scanner input = new Scanner(System.in);
		System.out.print("Score earned? ");
		return input.nextInt();
	}

	public static int typeScoreShifted() {
		Scanner input = new Scanner(System.in);
		System.out.print("Were scores shifted (1 = yes, 2 = no)? ");
		int scoreShifted = input.nextInt();
		if (scoreShifted != 1 && scoreShifted != 2) {
			System.out.println("Only accepts the answer of 1 or 2");
			scoreShifted = input.nextInt();
		}
		return scoreShifted;
	}

	public static int typeNumOfAsm() {
		Scanner input = new Scanner(System.in);
		System.out.print("Number of Assignment? ");
		return input.nextInt();
	}

	public static void typeAsmPoint(int n, int homeworkScore[]) {
		Scanner input = new Scanner(System.in);
		int[] homeworkScoreMax = new int[n + 1];
		homeworkScore[0] = 0;
		for (int i = 1; i <= n; i++) {
			System.out.print("Assignment " + i + " score and max? ");
			homeworkScore[i] = input.nextInt();
			homeworkScoreMax[i] = input.nextInt();
			if (homeworkScore[i] > homeworkScoreMax[i]) {
				System.out.println("Please enter the correct point of assignment " + i + " again");
				homeworkScore[i] = input.nextInt();
			}
		}
		for (int i = 1; i <= n; i++) {
			homeworkScore[0] += homeworkScoreMax[i];
		}
		if (homeworkScore[0] > 150) {
			homeworkScore[0] = 150;
		}
	}

	public static int totalPointCal(int scoreEarned, int shiftAmount) {
		int totalPoint = scoreEarned + shiftAmount;
		if (totalPoint <= 100) {
			return totalPoint;
		} else {
			return 100;
		}
	}

	public static double weightedScoreCal(double totalPoint, double weight, double maxPoint) {
		return (totalPoint / maxPoint * weight);
	}

	public static String gpaCal(double overall) {
		if (overall >= 85) {
			return "3.0 \n-- Your result is Excellent --\n";
		} else if (overall >= 75 && overall < 85) {
			return "2.0 \n Your result is Good\n";

		} else if (overall >= 60 && overall < 75) {
			return "1.0 \n-- Your result is Below Average -- \n";
		} else {
			return "0.0 \n-- You Failed, better try next time -- \n";
		}
	}
}
