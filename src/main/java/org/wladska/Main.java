package org.wladska;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		// Specify the path to your Java file
		String filePath = "YourJavaFile.java";
		try {
			String code = readCodeFromFile(filePath);
			if (code != null && !code.isEmpty()) {
				HalsteadMetrics metrics = calculateHalsteadMetrics(code);
				System.out.println("Halstead Metrics:");
				System.out.println("Program length (N): " + metrics.getProgramLength());
				System.out.println("Vocabulary size (n): " + metrics.getVocabularySize());
				System.out.println("Program volume (V): " + metrics.getProgramVolume());
				System.out.println("Difficulty (D): " + metrics.getDifficulty());
				System.out.println("Effort (E): " + metrics.getEffort());
				System.out.println("Time required to program (T): " + metrics.getTimeRequiredToProgram());
				System.out.println("Number of delivered bugs (B): " + metrics.getNumberOfDeliveredBugs());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readCodeFromFile(String filePath) throws IOException {
		StringBuilder code = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;

		while ((line = reader.readLine()) != null) {
			code.append(line).append("\n");
		}
		reader.close();
		return code.toString();
	}

	private static HalsteadMetrics calculateHalsteadMetrics(String code) {
		Set<String> uniqueOperators = new HashSet<>();
		Set<String> uniqueOperands = new HashSet<>();
		String[] tokens = code.split("\\s+|\\b");

		int N1 = 0; // Total number of operators
		int N2 = 0; // Total number of operands

		for (String token : tokens) {
			if (isOperator(token)) {
				uniqueOperators.add(token);
				N1++;
			} else if (isOperand(token)) {
				uniqueOperands.add(token);
				N2++;
			}
		}

		int n1 = uniqueOperators.size(); // Number of distinct operators
		int n2 = uniqueOperands.size(); // Number of distinct operands

		int programLength = N1 + N2; // N
		int vocabularySize = n1 + n2; // n
		double programVolume = programLength * (Math.log(vocabularySize) / Math.log(2)); // V

		double difficulty = (n1 / 2.0) * (N2 / (double) n2); // D
		double effort = difficulty * programVolume; // E
		double timeRequiredToProgram = effort / 18; // T
		double numberOfDeliveredBugs = Math.pow(effort, (2.0 / 3.0)) / 3000; // B

		return new HalsteadMetrics(programLength, vocabularySize, programVolume, difficulty, effort,
				timeRequiredToProgram, numberOfDeliveredBugs);
	}

	private static boolean isOperator(String token) {
		String[] operators = {"*", "/", "%", "+", "-", "<<", ">>", ">>>", "<", ">", "<=", ">=", "instanceof", "==",
							  "!=", "&", "^", "|", "&&", "||", "?", ":", "=", "+=", "-=" , "*=", "/=", "%=", "&=",
							  "^=", "|=", "<<=", ">>=", ">>>="};
		for (String op : operators) {
			if (token.equals(op)) {
				return true;
			}
		}
		return token.matches("(--|\\+\\+|~|!)\\w+|\\w+(--|\\+\\+)"); // check if unary operator
	}

	private static boolean isOperand(String token) {
		return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
	}
}