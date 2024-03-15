package org.wladska;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java HalsteadMetrics <file_path>");
			return;
		}

		String filePath = args[0];
		File file = new File(filePath);

		if (!file.exists()) {
			System.err.println("Error: File does not exist.");
			return;
		}

		try {
			String code = readCodeFromFile(filePath);
			if (!code.isEmpty()) {
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
			System.err.println("Error: Exception occurred: " + e);
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

	public static HalsteadMetrics calculateHalsteadMetrics(String code) {
		Set<String> uniqueOperators = new HashSet<>();
		Set<String> uniqueOperands = new HashSet<>();
		String[] tokens = code.split("\\s+|\\b");

		tokens = Arrays.stream(tokens)
				.filter(token -> !token.isEmpty())
				.toArray(String[]::new);

		int N1 = 0; // Total number of operators
		int N2 = 0; // Total number of operands

		int uniqueOperandOffset = 0;
		for (int i = 0; i < tokens.length; i++) {
			Pair unaryOperator = isUnaryOperator(tokens[i]);
			if (!unaryOperator.first.isEmpty() || isOperator(tokens[i])) {
				uniqueOperators.add(unaryOperator.first.isEmpty() ? tokens[i] : unaryOperator.first);
				N1++;

				if (isOperator(tokens[i])) {
					if (uniqueOperandOffset <= 0) {
						uniqueOperands.add(tokens[i-1]);
						uniqueOperands.add(tokens[i+1]);
						N2 += 2;
					} else {
						uniqueOperands.add(tokens[i+1]);
						N2++;
					}
					uniqueOperandOffset = 3;
				} else {
					N2++;
				}
			}
			uniqueOperandOffset--;
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

	private static Pair isUnaryOperator(String token) {
		if (token.startsWith("--")) {
			return new Pair("--expr", token.substring(2));
		}
		if (token.startsWith("++")) {
			return new Pair("++expr", token.substring(2));
		}
		if (token.startsWith("~")) {
			return new Pair("~expr", token.substring(1));
		}
		if (token.startsWith("!")) {
			return new Pair("!expr", token.substring(1));
		}
		if (token.endsWith("--")) {
			return new Pair("expr--", token.substring(0,token.length()-1));
		}
		if (token.endsWith("++")) {
			return new Pair("expr++", token.substring(0,token.length()-1));
		}
		return new Pair();
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
		return false;
	}
}