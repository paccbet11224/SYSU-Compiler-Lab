import java.io.*;
import java.util.*;

/**
 * 解析器类，用于将中缀表达式转换为后缀表达式（仅支持整数、+ 和 - 运算符）。
 */
class Parser {
	protected final List<Character> input = new ArrayList<>(); // 存储输入的字符
	protected final List<Character> output = new ArrayList<>(); // 存储转换后的后缀表达式
	private int currentIndex = 0; // 当前读取字符的索引
	private int currentChar; // 当前字符

	/**
	 * 构造函数，从标准输入读取一行字符作为表达式并初始化输入列表。
	 */
	public Parser() {
		try (Scanner scanner = new Scanner(System.in)) {
			String line = scanner.nextLine();
			for (char c : line.toCharArray()) {
				input.add(c);
			}
		}
		if (!input.isEmpty()) {
			currentChar = input.get(0);
		}
		System.out.println("------------------------------------------");
	}

	/**
	 * 解析整个表达式，将其从中缀转换为后缀表示并输出结果。
	 *
	 * @throws IOException 若输入流异常
	 */
	public void parseExpression() throws IOException {
		parseTerm();
		parseRest();
		System.out.print("转换为后缀表达式的结果为：");
		for (char c : output) {
			System.out.print(c);
		}
	}

	/**
	 * 解析一个“项”，即一个数字字符，添加至输出中。
	 *
	 * @throws IOException 若解析过程中遇到错误
	 */
	private void parseTerm() throws IOException {
		while (!isDigit(currentChar)) {
			reportError("语法错误，缺少左运算量，已自动忽略此字符", currentIndex);
			advance();
			if (isEnd())
				return;
		}
		output.add((char) currentChar);
		advance();
	}

	/**
	 * 解析剩余表达式，支持连续运算和错误处理。
	 *
	 * @throws IOException 若解析过程中遇到错误
	 */
	private void parseRest() throws IOException {
		while (!isEnd()) {
			if (isDigit(currentChar)) {
				reportError("语法错误，这两个运算量间缺少运算符，已自动忽略第二个运算量", currentIndex - 1, currentIndex);
				advance();
			} else if (currentChar == '+' || currentChar == '-') {
				int op = currentChar;
				advance();
				parseTerm();
				output.add((char) op);
			} else if (currentChar == ' ') {
				reportError("词法错误，此处不应该有空格，已自动忽略", currentIndex);
				advance();
			} else {
				reportError("词法错误，非法运算符，已自动忽略，只支持+与-", currentIndex);
				advance();
			}
		}
	}

	/**
	 * 读取下一个字符并更新当前字符信息。
	 */
	private void advance() {
		currentIndex++;
		if (currentIndex < input.size()) {
			currentChar = input.get(currentIndex);
		} else {
			currentChar = -1;
		}
	}

	/**
	 * 判断字符是否为数字。
	 *
	 * @param c 字符
	 * @return 是否为 0-9 之间的数字
	 */
	private boolean isDigit(int c) {
		return c >= '0' && c <= '9';
	}

	/**
	 * 判断是否已经解析完所有输入。
	 *
	 * @return 输入是否结束
	 */
	private boolean isEnd() {
		return currentChar == -1;
	}

	/**
	 * 输出单个错误提示和位置指示。
	 *
	 * @param message 错误信息
	 * @param index   出错位置
	 */
	private void reportError(String message, int index) {
		printInput();
		printPointer(index);
		System.out.println(message);
		System.out.println("------------------------------------------");
	}

	/**
	 * 输出两个位置之间的错误提示和位置指示。
	 *
	 * @param message 错误信息
	 * @param index1  第一个相关位置
	 * @param index2  第二个相关位置
	 */
	private void reportError(String message, int index1, int index2) {
		printInput();
		printDoublePointer(index1, index2);
		System.out.println(message);
		System.out.println("------------------------------------------");
	}

	/**
	 * 打印输入的表达式。
	 */
	private void printInput() {
		for (char c : input) {
			System.out.print(c);
		}
		System.out.println();
	}

	/**
	 * 打印单个字符位置指示（^）。
	 *
	 * @param index 指示的位置
	 */
	private void printPointer(int index) {
		for (int i = 0; i < index; i++) {
			System.out.print(' ');
		}
		System.out.println('^');
	}

	/**
	 * 打印两个字符之间的错误指示（^^）。
	 *
	 * @param index1 第一个字符位置
	 * @param index2 第二个字符位置
	 */
	private void printDoublePointer(int index1, int index2) {
		for (int i = 0; i < Math.min(index1, index2); i++) {
			System.out.print(' ');
		}
		System.out.println("^^");
	}

	/**
	 * 静态方法：将中缀表达式字符串转换为后缀表达式字符串。
	 *
	 * @param input 输入的中缀表达式
	 * @return 后缀表达式
	 * @throws IOException 若解析失败
	 */
	public static String convert(String input) throws IOException {
		Parser parser = new Parser();
		parser.parseExpression();
		StringBuilder sb = new StringBuilder();
		for (char ch : parser.output) {
			sb.append(ch);
		}
		return sb.toString();
	}
}

/**
 * 主类，负责运行程序并触发中缀转后缀转换。
 */
public class Postfix {

	/**
	 * 程序入口，提示用户输入表达式并输出转换结果。
	 *
	 * @param args 命令行参数
	 * @throws IOException 若解析过程中出现异常
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Input an infix expression and output its postfix notation:");
		new Parser().parseExpression();
		System.out.println("\n------------------------------------------");
		System.out.println("End of program.");
	}
}
