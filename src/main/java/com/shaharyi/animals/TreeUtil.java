import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class TreeUtil {

	public static <T> BinNode<T> completeTreeFromLevelOrder(T[] a, int i) {
		if (i >= a.length)
			return null;
		BinNode<T> r = new BinNode<T>(a[i]);
		r.setLeft(completeTreeFromLevelOrder(a, 2 * i + 1));
		r.setRight(completeTreeFromLevelOrder(a, 2 * i + 2));
		return r;
	}

	public static <T> void writeToFile(BinNode<T> root, String fileName) {
		try {
			File file = new File(fileName);
			PrintWriter pw = new PrintWriter(file);
			writeBinaryTree(root, pw);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static BinNode<String> readFromFile(String fileName) {
		BinNode<String> r = null;
		try {
			File file = new File(fileName);
			Scanner fs = new Scanner(file);
			r = readBinaryTree(fs);
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return r;
	}

	static <T> void writeBinaryTree(BinNode<T> p, PrintWriter pw) {
		if (p == null) {
			pw.println("NULL");
		} else {
			pw.println(p.getValue());
			writeBinaryTree(p.getLeft(), pw);
			writeBinaryTree(p.getRight(), pw);
		}
	}

	static BinNode<String> readBinaryTree(Scanner fileScanner) {
		if (!fileScanner.hasNextLine())
			return null;
		String value = fileScanner.nextLine();
		if (value.equals("NULL"))
			return null;
		BinNode<String> root = new BinNode<String>(value);
		root.setLeft(readBinaryTree(fileScanner));
		root.setRight(readBinaryTree(fileScanner));
		return root;
	}
}